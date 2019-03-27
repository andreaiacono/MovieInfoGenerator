package me.andreaiacono.generator.service

import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.model.fromXml
import java.awt.image.BufferedImage

class MovieManager(config: Config) {

    val createdMovieDirs = mutableListOf<String>()
    val notCreatedMovieDirs = mutableListOf<String>()
    val nasService = NasService(config.nasUrl)

    fun loadData() {
        println("Loading from NAS")
        nasService.getMoviesDirectories()
            .filter { it.isDirectory }
            .forEach { dir ->
                println("Reading [${dir.name}]")
                val xml = dir
                    .listFiles()
                    .firstOrNull {
                        !it.name.startsWith(".") && it.name.toLowerCase().endsWith(".xml")
                    }
                if (xml == null) {
                    notCreatedMovieDirs.add(dir.name)
                }
                else {
                    val movie = fromXml(xml.inputStream.readBytes().toString(Charsets.UTF_8))
                    createdMovieDirs.add(movie.title)
                }
            }
    }

    fun getMoviePoster(dirName: String): BufferedImage {
        return nasService.getPoster(dirName)
    }

}