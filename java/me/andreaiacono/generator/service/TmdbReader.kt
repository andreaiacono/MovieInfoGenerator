package me.andreaiacono.generator.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.andreaiacono.generator.model.Search
import me.andreaiacono.generator.model.TmdbMovie
import me.andreaiacono.generator.model.TmdbMovieCredits
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO



class TmdbReader (val url: String, val apiKey: String, val language: String) {

    val size = "w200"
    val searchUrl = url + "?api_key=$apiKey&language=$language&s="
    val movieReqString = "?api_key=$apiKey&language=$language"
    val imageUrl = "http://image.tmdb.org/t/p/$size/"

    private fun searchMovieInfo(title: String): String = URL(searchUrl + title.replace(" ", "%20")).readText()

    private val jsonMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun searchMovie(title: String): Search {
        val jsonResult = searchMovieInfo(title)
        return jsonMapper.readValue(jsonResult, Search::class.java)
    }

    fun getMovieInfo(id : String): TmdbMovie {
        val jsonResult = URL("${url}3/movie/$id$movieReqString").readText()
        return jsonMapper.readValue(jsonResult, TmdbMovie::class.java)
    }

    fun getMovieCredits(id : String): TmdbMovieCredits       {
        val jsonResult = URL("${url}3/movie/$id/credits$movieReqString").readText()
        return jsonMapper.readValue(jsonResult, TmdbMovieCredits::class.java)
    }

    fun getPoster(imageId: String): BufferedImage {
            val url = URL("$imageUrl$imageId")
            return ImageIO.read(url)
    }

    fun getMovieActors(id: String): List<String?> {
        return getMovieCredits(id).cast!!.map { it!!.name }.toList()
    }

    fun getMovieActors(id: String, take: Int): List<String?> {
        return getMovieCredits(id).cast!!.take(take).map { it!!.name }.toList()
    }
}