package me.andreaiacono.generator

import me.andreaiacono.generator.service.TmdbReader
import me.andreaiacono.generator.gui.loadConfig
import net.bramp.ffmpeg.FFprobe





fun main(args: Array<String>) {

    val ffprobe = FFprobe("/usr/local/bin/ffprobe")
    val probeResult = ffprobe.probe("/andrea/mnt/Snowden/Snowden.mkv")
//    val probeResult = ffprobe.probe("/andrea/mnt/Lei/Lei - Her (2013).ita.eng.sub.ita.MIRCrew.avi")
//    val probeResult = ffprobe.probe("/andrea/mnt/The Forger/The.Forger.IL.Falsario.2014.BDRip.1080p.DTS.ITA.ENG.x264.sub.DVS.mkv")
//    val probeResult = ffprobe.probe("/andrea/mnt/Shrek/Shrek_t00.mkv")

    val format = probeResult.getFormat()
    System.out.format(
        "%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
        format.filename,
        format.format_long_name,
        format.duration
    )

    val stream = probeResult.getStreams()[0]
    println("\n${stream.width}x${stream.height} ${(stream.avg_frame_rate.numerator/stream.avg_frame_rate.denominator)}f/s [${stream.codec_name}]")
    System.out.format("Ratio: %s ; Width: %dpx ; Height: %dpx",
        stream.display_aspect_ratio,
        stream.width,
        stream.height
    );
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
////    val template = Template()
//////    val cover = ImageIO.read(File(Generator::class.java.getResource("/cover.jpg").file))
//////    val background = ImageIO.read(File(Generator::class.java.getResource("/background.jpg").file))
//    val tmdbReader = TmdbReader(
//        config.tmdbUrl,
//        config.tmdbApiKey,
//        "it-IT"
//    )
////
//////    val search = TmdbReader.("Blade Runner")
//////    println(search.search.joinToString { it.title })
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
////    val generator = Generator(movie, template)
////    val cover = Thumbnails.of(tmdbReader.getPoster(movieInfo.posterPath!!)).forceSize(154, 231).asBufferedImage()
////    val background = Thumbnails.of(tmdbReader.getBackground(movieInfo.backdropPath!!)).forceSize(1920, 1080).asBufferedImage()
////    ImageIO.write(generator.generate(background, cover), "PNG", File("result.png"))
////    }
}

