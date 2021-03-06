package me.andreaiacono.generator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Config(

    @JsonProperty("tmdbApiKey")
    var tmdbApiKey: String,

    @JsonProperty("tmdbUrl")
    var tmdbUrl: String,

    @JsonProperty("nasUrl")
    var nasUrl: String,

    @JsonProperty("moviesDir")
    var moviesDir: String,

    @JsonProperty("ffprobePath")
    var ffprobePath: String
)