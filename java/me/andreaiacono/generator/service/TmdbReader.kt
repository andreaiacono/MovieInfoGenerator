package me.andreaiacono.generator.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.andreaiacono.generator.model.Search
import me.andreaiacono.generator.model.TmdbMovie
import me.andreaiacono.generator.model.TmdbMovieCredits
import me.andreaiacono.generator.model.TmdbMovieWithCredits
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO



class TmdbReader (val url: String, val apiKey: String, val language: String) {

    /// available sizes:
    // "backdrop_sizes": [
    //  "w300",
    //  "w780",
    //  "w1280",
    //  "original"
    //],
    //"logo_sizes": [
    //  "w45",
    //  "w92",
    //  "w154",
    //  "w185",
    //  "w300",
    //  "w500",
    //  "original"
    //],
    //"poster_sizes": [
    //  "w92",
    //  "w154",
    //  "w185",
    //  "w342",
    //  "w500",
    //  "w780",
    //  "original"
    //],
    //"profile_sizes": [
    //  "w45",
    //  "w185",
    //  "h632",
    //  "original"
    //],
    //"still_sizes": [
    //  "w92",
    //  "w185",
    //  "w300",
    //  "original"
    //]
    val searchUrl = url + "?api_key=$apiKey&language=$language&s="
    val movieReqString = "?api_key=$apiKey&language=$language"
    val urlImage154 = "http://image.tmdb.org/t/p/w154/"
    val urlImageOriginal = "http://image.tmdb.org/t/p/original/"

    private fun searchMovieInfo(title: String): String = URL(searchUrl + title.replace(" ", "%20")).readText()

    private val jsonMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun searchMovie(title: String): Search {
        val jsonResult = searchMovieInfo(title)
        return jsonMapper.readValue(jsonResult, Search::class.java)
    }

    fun getMovieInfo(id : String): TmdbMovieWithCredits {
        val jsonResult = URL("${url}3/movie/$id$movieReqString&append_to_response=credits").readText()
        println(jsonResult)
        return jsonMapper.readValue(jsonResult, TmdbMovieWithCredits::class.java)
    }

    private fun getMovieCredits(id : String): TmdbMovieCredits       {
        val jsonResult = URL("${url}3/movie/$id/credits$movieReqString").readText()
        return jsonMapper.readValue(jsonResult, TmdbMovieCredits::class.java)
    }

    fun getPoster(imageId: String): BufferedImage {
            val url = URL("$urlImage154$imageId")
            return ImageIO.read(url)
    }
    fun getBackground(imageId: String): BufferedImage {
            val url = URL("$urlImageOriginal$imageId")
            return ImageIO.read(url)
    }

    fun getMovieActors(id: String): List<String?> {
        return getMovieActors(id, Int.MAX_VALUE)
    }

    fun getMovieActors(id: String, take: Int): List<String?> {
        return getMovieCredits(id).cast!!.take(take).map { it!!.name }.toList()
    }
}