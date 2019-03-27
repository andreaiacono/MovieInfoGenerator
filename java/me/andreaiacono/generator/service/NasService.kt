package me.andreaiacono.generator.service

import jcifs.smb.SmbFile
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class NasService(val url: String) {

    val LOG_TAG = this.javaClass.name

    fun getMoviesDirectories(): Array<SmbFile> {
        val moviesRoot = SmbFile(url)
        return moviesRoot.listFiles()
    }

    private fun getFullImage(dirName: String, filename: String): BufferedImage {
        val fullName = "$url$dirName/$filename"
        println("loading $fullName")
        return ImageIO.read(SmbFile(fullName).inputStream)
    }

    fun getThumbnail(dirName: String): BufferedImage = getFullImage(dirName, "folder.jpg")

    fun getPoster(dirName: String): BufferedImage = getFullImage(dirName, "about.jpg")

}

