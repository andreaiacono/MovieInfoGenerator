package me.andreaiacono.generator

import me.andreaiacono.generator.model.Movie
import me.andreaiacono.generator.service.OpenMovieReader
import me.andreaiacono.generator.service.TmdbReader
import net.coobird.thumbnailator.Thumbnails
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.mp4.MP4Parser
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO
import java.lang.System.exit
import org.apache.tika.sax.BodyContentHandler


fun main(args: Array<String>) {

////
//
//    val file = File("/andrea/mnt/quiz show/title00.mkv")
//
//    val handler = BodyContentHandler()
//    val metadata = Metadata()
//    val inputstream = FileInputStream(file)
//    val pcontext = ParseContext()
//
//    //Html parser
//    val MP4Parser = MP4Parser()
//    MP4Parser.parse(inputstream, handler, metadata, pcontext)
//    println("Contents of the document:  :$handler")
//    println("Metadata of the document:")
//    val metadataNames = metadata.names()
//
//    for (name in metadataNames) {
//        println(name + ": " + metadata.get(name))
//    }
//
//    exit(-1)
    val template = Template()
//    val cover = ImageIO.read(File(Generator::class.java.getResource("/cover.jpg").file))
//    val background = ImageIO.read(File(Generator::class.java.getResource("/background.jpg").file))
    val openMovieReader = OpenMovieReader("http://www.omdbapi.com/", "13c1fc2a")
    val tmdbReader = TmdbReader(
        "https://api.themoviedb.org/",
        "b77a516e6e552bf27fb2f38146d790d0",
        "it-IT"
    )

//    val search = TmdbReader.("Blade Runner")
//    println(search.search.joinToString { it.title })
    val id = "606"
//    val id = "329865"
    val movieInfo = tmdbReader.getMovieInfo(id)
//    val tmdbMovieCredits = tmdbReader.getMovieCredits(id)
    val actors = movieInfo.getActors(5)
    val directors = movieInfo.getDirectors()
    println("movie: ${movieInfo}")
    println("movie actors: $actors")
    println("movie directors: $directors")

    val movie = Movie(
        movieInfo.title!!,
        movieInfo.overview!!,
        movieInfo.getActors(5),
        movieInfo.getDirectors(),
        movieInfo.runtime!!,
        "1080p @ 29fps"
    )
    val generator = Generator(movie, template)
    val cover = Thumbnails.of(tmdbReader.getPoster(movieInfo.posterPath!!)).forceSize(154, 231).asBufferedImage()
    val background = Thumbnails.of(tmdbReader.getBackground(movieInfo.backdropPath!!)).forceSize(1920, 1080).asBufferedImage()
    ImageIO.write(generator.generate(background, cover), "PNG", File("result.png"))
}