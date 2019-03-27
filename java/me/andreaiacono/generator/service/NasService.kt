package me.andreaiacono.generator.service

import jcifs.smb.SmbFile

class NasService(val url: String) {

    val LOG_TAG = this.javaClass.name

    fun getMoviesDirectories(): Array<SmbFile> {
        val moviesRoot = SmbFile(url)
        return moviesRoot.listFiles()
    }
}

