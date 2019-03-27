package me.andreaiacono.generator

import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val template = Template()
    val cover = ImageIO.read(File(Generator::class.java.getResource("/cover.jpg").file))
    val background = ImageIO.read(File(Generator::class.java.getResource("/background.jpg").file))
    val openMovieReader = OpenMovieReader("http://www.omdbapi.com/", "13c1fc2a")
    val tmdbReader = TmdbReader("https://api.themoviedb.org/", "b77a516e6e552bf27fb2f38146d790d0", "it-IT")

//    val search = TmdbReader.("Blade Runner")
//    println(search.search.joinToString { it.title })
    val tmdbMovie = tmdbReader.getMovieInfo("329865")

    println("movie: ${tmdbMovie}")
//    val movie = Movie(
//        tmdbMovie.title!!,
//        tmdbMovie.overview!! ,
//        tmdbMovie.
//        listOf("Denis Villeneuve"),
//        116,
//        MovieResolution.ULTRA_HD
//    )
//    val generator = Generator(movie, background, cover, template)
//
//    ImageIO.write(generator.generate(), "PNG", File("result.png"))
}