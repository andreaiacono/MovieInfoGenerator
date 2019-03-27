package me.andreaiacono.generator

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.awt.image.BufferedImage
import java.io.BufferedInputStream
import java.net.URL
import java.io.IOException
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

    fun getPoster(uri: String): BufferedImage {
            val url = URL("${imageUrl}$uri")
            return ImageIO.read(url)
    }
}