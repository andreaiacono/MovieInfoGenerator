package me.andreaiacono.generator.service

import jcifs.smb.SmbFile
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class NasService(val url: String) {

    val movieExtensions: Set<String> =
        setOf("mpg", "mpeg", "mp4", "m4p", "m4v", "avi", "mkv", "webm", "vob", "mov", "qt", "wmv")

    fun getMoviesDirectories(): Array<SmbFile> {
        val moviesRoot = SmbFile(url)
        return moviesRoot.listFiles()
    }

    private fun getFullImage(dirName: String, filename: String): BufferedImage {
        val fullName = "$url$dirName/$filename"
        return ImageIO.read(SmbFile(fullName).inputStream)
    }

    fun getThumbnail(dirName: String): BufferedImage = getFullImage(dirName, "folder.jpg")

    fun getPoster(dirName: String): BufferedImage = getFullImage(dirName, "about.jpg")

    fun getVideoFilename(dirName: String): String? {
        val fullDirname = "$url$dirName"
        println("get video dir: $fullDirname")
        return SmbFile(fullDirname).listFiles().map { it.name }.firstOrNull { it.takeLast(3).toLowerCase() in movieExtensions }
    }

}

