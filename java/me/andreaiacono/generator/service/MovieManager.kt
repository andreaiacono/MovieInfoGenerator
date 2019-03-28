package me.andreaiacono.generator.service

import me.andreaiacono.generator.Generator
import me.andreaiacono.generator.Template
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.model.fromXml
import me.andreaiacono.generator.util.getMovieMetadata
import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage
import java.lang.Math.min

class MovieManager(val config: Config) {

    val createdMovieDirs = mutableListOf<String>()
    val unknowndMovieDirs = mutableListOf<String>()
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
                    unknowndMovieDirs.add(dir.name.dropLast(1))
                } else {
                    val movie = fromXml(xml.inputStream.readBytes().toString(Charsets.UTF_8))
                    createdMovieDirs.add(movie.title)
                }
            }
        unknowndMovieDirs.sort()
        createdMovieDirs.sort()
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

    fun generatePoster(id: String, dirName: String): Pair<BufferedImage, String> {

        val videoFilename = nasService.getVideoFilename(dirName)
        if (videoFilename != null) {
            val template = Template()
            val movieInfo = tmdbReader.getMovieInfo(id)
            val videoInfo = getMovieMetadata(config.ffprobePath, "${config.moviesDir}$dirName$videoFilename")
            val generator = Generator(movieInfo, videoInfo, template)
            val cover =
                if (movieInfo.posterPath != null) Thumbnails.of(tmdbReader.getPoster(movieInfo.posterPath!!)).forceSize(
                    154,
                    231
                ).asBufferedImage() else BufferedImage(1, 1, 1)
            val background =
                if (movieInfo.backdropPath != null) Thumbnails.of(tmdbReader.getBackground(movieInfo.backdropPath!!)).forceSize(
                    1920,
                    1080
                ).asBufferedImage() else BufferedImage(1, 1, 1)
            return Pair(generator.generate(background, cover), movieInfo.toXml())
        }

        return Pair(BufferedImage(1, 1, 1), "")
    }
}