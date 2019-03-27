package me.andreaiacono.generator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Config(

    @JsonProperty("tmdbApiKey")
    var tmdbApiKey: String,

    @JsonProperty("tmdbUrl")
    var tmdbUrl: String
)