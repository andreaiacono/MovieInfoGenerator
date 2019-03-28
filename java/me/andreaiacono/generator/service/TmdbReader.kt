package me.andreaiacono.generator.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.andreaiacono.generator.model.*
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO


class TmdbReader(val url: String, val apiKey: String, val language: String) {

//     available images sizes:
//    "backdrop_sizes": ["w300", "w780", "w1280", "original"],
//    "logo_sizes": ["w45", "w92", "w154", "w185", "w300", "w500", "original"],
//    "poster_sizes": ["w92", "w154", "w185", "w342", "w500", "w780", "original"],
//    "profile_sizes": ["w45", "w185", "h632", "original"],
//    "still_sizes": ["w92", "w185", "w300", "original"]

    val movieReqString = "?api_key=$apiKey&language=$language"
    val urlImage154 = "http://image.tmdb.org/t/p/w154/"
    val urlImageOriginal = "http://image.tmdb.org/t/p/original/"

    private val jsonMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun searchMovie(title: String, page: Int = 1): TmdbSearch {
        val jsonResult = URL("${url}3/search/movie$movieReqString&query=${title.replace(" ", "%20")}&include_adult=false&page=$page").readText()
        return jsonMapper.readValue(jsonResult, TmdbSearch::class.java)
    }

    fun getMovieInfo(id: String): TmdbMovieInfo {
        val jsonResult = URL("${url}3/movie/$id$movieReqString&append_to_response=credits").readText()
        println(jsonResult)
        return jsonMapper.readValue(jsonResult, TmdbMovieInfo::class.java)
    }

    fun getCover(imageId: String): BufferedImage {
        val url = URL("$urlImage154$imageId")
        return ImageIO.read(url)
    }

    fun getBackground(imageId: String): BufferedImage {
        val url = URL("$urlImageOriginal$imageId")
        return ImageIO.read(url)
    }
}