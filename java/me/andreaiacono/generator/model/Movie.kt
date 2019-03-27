package me.andreaiacono.generator.model

data class Movie(val title: String, val synopsis: String, val cast: List<String?>, val directors: List<String?>, val duration: Int?, val resolution: String)