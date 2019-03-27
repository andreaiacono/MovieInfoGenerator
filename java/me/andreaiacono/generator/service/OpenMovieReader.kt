package me.andreaiacono.generator.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.andreaiacono.generator.model.ApiMovie
import me.andreaiacono.generator.model.Search
import java.net.URL

class OpenMovieReader(url: String, apiKey: String) {

    val searchUrl = url + "?apikey=$apiKey&type=movie&s="
    val movieUrl = url + "?apikey=$apiKey&type=movie&i="

    private fun searchMovieInfo(title: String): String = URL(searchUrl + title.replace(" ", "%20")).readText()

    private fun getMovieInfoString(movieId: String): String = URL(movieUrl + movieId).readText()

    private val jsonMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun searchMovie(title: String): Search {
        val jsonResult = searchMovieInfo(title)
        return jsonMapper.readValue(jsonResult, Search::class.java)
    }

    fun getMovieInfo(imdbId : String): ApiMovie {
        val jsonResult = getMovieInfoString(imdbId)
        return jsonMapper.readValue(jsonResult, ApiMovie::class.java)
    }
}