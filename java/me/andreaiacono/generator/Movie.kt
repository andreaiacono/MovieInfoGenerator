package main.java.me.andreaiacono.generator

data class Movie(val title: String, val synopsis: String, val cast: List<String>, val directors: List<String>, val duration: Int, val resolution: MovieResolution)