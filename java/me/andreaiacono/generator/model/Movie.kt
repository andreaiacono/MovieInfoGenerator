package me.andreaiacono.generator.model

import me.andreaiacono.generator.MovieResolution

data class Movie(val title: String, val synopsis: String, val cast: List<String>, val directors: List<String>, val duration: Int, val resolution: MovieResolution)