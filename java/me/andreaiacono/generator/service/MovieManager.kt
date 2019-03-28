package me.andreaiacono.generator.service

import me.andreaiacono.generator.Generator
import me.andreaiacono.generator.Template
import me.andreaiacono.generator.gui.util.ErrorForm
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.model.fromXml
import me.andreaiacono.generator.util.getMovieMetadata
import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Math.min
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.IIOImage
import javax.imageio.stream.FileImageOutputStream
import javax.imageio.plugins.jpeg.JPEGImageWriteParam

class MovieManager(val config: Config) {

    val createdMovieDirs = mutableListOf<Pair<String, String>>()
    val unknownMovieDirs = mutableListOf<Pair<String, String>>()
    val nasService = NasService(config.nasUrl)
    val tmdbReader = TmdbReader(config.tmdbUrl, config.tmdbApiKey, "it-IT")

    fun loadData() {
        println("Loading from NAS")
        nasService.getMoviesDirectories()
            .filter { it.isDirectory && !it.name.startsWith(".") }
            .take(15)
            .forEach { dir ->
                println("Reading [${dir.name}]")
                val xml = dir
                    .listFiles()
                    .firstOrNull { !it.name.startsWith(".") && it.name.toLowerCase().endsWith(".xml") }
                if (xml == null) {
                    unknownMovieDirs.add(Pair(dir.name.dropLast(1), dir.name.dropLast(1)))
                } else {
                    val movie = fromXml(xml.inputStream.readBytes().toString(Charsets.UTF_8))
                    println("added ${dir.name}: [${movie.title}]")
                    createdMovieDirs.add(Pair(movie.title, dir.name.dropLast(1)))
                }
            }
        unknownMovieDirs.sortBy { it.first }
        createdMovieDirs.sortBy { it.first }
    }

    fun searchMovie(title: String): List<String> {

        val search = tmdbReader.searchMovie(title)
        val results = mutableListOf<String>()
        results.addAll(search.results?.map { "${it?.title} [${it?.releaseDate}] ${it?.id}" }!!.toList())

        for (i in 2..min(search.totalPages!!, 10)) {
            val pageSearch = tmdbReader.searchMovie(title, i)
            results.addAll(pageSearch.results?.map { "${it?.title} [${it?.releaseDate}] ${it?.id}" }!!.toList())
        }
        results.sort()
        return results
    }

    fun getMoviePoster(dirName: String): BufferedImage {
        return nasService.getPoster(dirName)
    }

    fun generatePoster(id: String, dirName: String): Triple<BufferedImage, String, String> {

        val videoFilename = nasService.getVideoFilename(dirName)
        if (videoFilename != null) {
            val template = Template()
            val movieInfo = tmdbReader.getMovieInfo(id)
            val videoInfo = getMovieMetadata(config.ffprobePath, "${config.moviesDir}$dirName$videoFilename")
            val generator = Generator(movieInfo, videoInfo, template)
            val cover =
                if (movieInfo.posterPath != null) Thumbnails.of(tmdbReader.getCover(movieInfo.posterPath!!)).forceSize(
                    154,
                    231
                ).asBufferedImage() else BufferedImage(1, 1, 1)
            val background =
                if (movieInfo.backdropPath != null) Thumbnails.of(tmdbReader.getBackground(movieInfo.backdropPath!!)).forceSize(
                    1920,
                    1080
                ).asBufferedImage() else BufferedImage(1, 1, 1)
            return Triple(generator.generate(background, cover), movieInfo.toXml(), movieInfo.posterPath!!)
        }

        return Triple(BufferedImage(1, 1, 1), "", "")
    }

    fun saveData(image: BufferedImage?, xml: String, dirName: String, id: String, coverUri: String) {
        try {
            val xmlFilename = "${config.moviesDir}${dirName}info.xml"
            println("Writing xml to $xmlFilename")
            File(xmlFilename).writeText(xml)

            val jpegParams = JPEGImageWriteParam(null)
            jpegParams.compressionMode = ImageWriteParam.MODE_EXPLICIT
            jpegParams.compressionQuality = 0.75f

            val posterFilename = "${config.moviesDir}${dirName}about.jpg"
            println("Writing poster to $posterFilename")
            val posterWriter = ImageIO.getImageWritersByFormatName("jpg").next()
            posterWriter.output = FileImageOutputStream(File(posterFilename))
            posterWriter.write(null, IIOImage(image, null, null), jpegParams)

            val coverFilename = "${config.moviesDir}${dirName}folder.jpg"
            println("Writing cover to $coverFilename")

            jpegParams.compressionQuality = 0.95f
            val coverWriter = ImageIO.getImageWritersByFormatName("jpg").next()
            coverWriter.output = FileImageOutputStream(File(coverFilename))
            coverWriter.write(null, IIOImage(tmdbReader.getCover(coverUri), null, null), jpegParams)
        }
        catch (ex: Exception) {
            ErrorForm(ex).isVisible = true
        }
    }
}



