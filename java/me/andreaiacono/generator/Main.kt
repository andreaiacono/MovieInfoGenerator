package me.andreaiacono.generator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import jcifs.smb.SmbFile
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.model.Movie
import me.andreaiacono.generator.service.NasService
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
import tornadofx.launch
import java.awt.EventQueue
import javax.swing.JFrame



fun mainw(args: Array<String>) {

//    val config = loadConfig()
//    val nas = NasService(config.nasUrl)
//
//    val missingMovieDirs = nas.getMoviesDirectories().filter { it.isDirectory && it.listFiles().none{ it.name.toLowerCase().endsWith(".xml") }  }.joinToString { it.name + "\n" }
//    print(missingMovieDirs)

////
//
//    launch<MyApp>(args)
////
//    val file = SmbFile("/andrea/mnt/Shrek/Shrek_t00.mkv")
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
//
//    val config = loadConfig()
//    val template = Template()
////    val cover = ImageIO.read(File(Generator::class.java.getResource("/cover.jpg").file))
////    val background = ImageIO.read(File(Generator::class.java.getResource("/background.jpg").file))
//    val tmdbReader = TmdbReader(
//        config.tmdbUrl,
//        config.tmdbApiKey,
//        "it-IT"
//    )
//
////    val search = TmdbReader.("Blade Runner")
////    println(search.search.joinToString { it.title })
//    val id = "606"
////    val id = "329865"
//    val movieInfo = tmdbReader.getMovieInfo(id)
////    val tmdbMovieCredits = tmdbReader.getMovieCredits(id)
//    val actors = movieInfo.getActors(5)
//    val directors = movieInfo.getDirectors()
//    println("movie: ${movieInfo}")
//    println("movie actors: $actors")
//    println("movie directors: $directors")
//
//    val movie = Movie(
//        movieInfo.title!!,
//        movieInfo.overview!!,
//        movieInfo.getActors(5),
//        movieInfo.getDirectors(),
//        movieInfo.runtime!!,
//        "1080p @ 29fps"
//    )
//    val generator = Generator(movie, template)
//    val cover = Thumbnails.of(tmdbReader.getPoster(movieInfo.posterPath!!)).forceSize(154, 231).asBufferedImage()
//    val background = Thumbnails.of(tmdbReader.getBackground(movieInfo.backdropPath!!)).forceSize(1920, 1080).asBufferedImage()
//    ImageIO.write(generator.generate(background, cover), "PNG", File("result.png"))
//    }
}

