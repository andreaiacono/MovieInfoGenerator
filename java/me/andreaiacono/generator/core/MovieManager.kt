package me.andreaiacono.generator.service

import me.andreaiacono.generator.core.MoviePreview
import me.andreaiacono.generator.gui.util.ErrorForm
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.model.TmbdMovieImages
import me.andreaiacono.generator.model.fromXml
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
    val moviePreview = MoviePreview(this)

    fun loadData() {
        println("Loading from NAS")
        nasService.getMoviesDirectories()
            .filter { it.isDirectory && !it.name.startsWith(".") }
            .forEach { dir ->
                println("Reading [${dir.name}]")
                val xml = dir
                    .listFiles()
                    .firstOrNull { !it.name.startsWith(".") && it.name.toLowerCase().endsWith(".xml") }
                if (xml == null) {
                    unknownMovieDirs.add(Pair(dir.name.dropLast(1), dir.name.dropLast(1)))
                } else {
                    val movie = fromXml(xml.inputStream.readBytes().toString(Charsets.UTF_8))
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

        for (i in 2..min(search.totalPages!!, 5)) {
            val pageSearch = tmdbReader.searchMovie(title, i)
            results.addAll(pageSearch.results?.map { "${it?.title} [${it?.releaseDate}] ${it?.id}" }!!.toList())
        }
        results.sort()
        return results
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
            val posterWriter = ImageIO.getImageWritersByFormatName("jpg").next()
            println("Writing poster to $posterFilename")
            posterWriter.output = FileImageOutputStream(File(posterFilename))
            posterWriter.write(null, IIOImage(image, null, null), jpegParams)

            val coverFilename = "${config.moviesDir}${dirName}folder.jpg"
            jpegParams.compressionQuality = 0.95f
            val coverWriter = ImageIO.getImageWritersByFormatName("jpg").next()
            println("Writing cover to $coverFilename")
            coverWriter.output = FileImageOutputStream(File(coverFilename))
            coverWriter.write(null, IIOImage(tmdbReader.getSmallSizePoster(coverUri), null, null), jpegParams)
        }
        catch (ex: Exception) {
            ErrorForm(ex).isVisible = true
        }
    }

    fun getAlternativeImages(id: String): TmbdMovieImages {
        return tmdbReader.getAlternativeImages(id)
    }

    fun getMoviePoster(dirName: String): BufferedImage {
        return nasService.getPoster(dirName)
    }

    fun getAlternativeImageThumb(filePath: String?): BufferedImage {
        return tmdbReader.getSmallSizeBackdrop(filePath ?: "null")
    }
    fun getAlternativeImageFullsize(filePath: String?): BufferedImage {
        return tmdbReader.getOriginalSizeImage(filePath ?: "null")
    }

    fun generatePoster(id: String, dirName: String, chosenBackgroundImage: BufferedImage? = null): Triple<BufferedImage, String, String> {
        return moviePreview.generatePoster(id, dirName, chosenBackgroundImage)
    }
}



