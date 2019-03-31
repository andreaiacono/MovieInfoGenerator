package me.andreaiacono.generator.core

import me.andreaiacono.generator.Generator
import me.andreaiacono.generator.Template
import me.andreaiacono.generator.model.TmdbMovieInfo
import me.andreaiacono.generator.util.EMPTY_IMAGE
import me.andreaiacono.generator.util.getMovieMetadata
import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage

class MoviePreview(val movieManager: MovieManager) {

    val template = Template()

    var cover: BufferedImage = EMPTY_IMAGE
    var videoInfo: String = ""
    var movieInfo: TmdbMovieInfo = TmdbMovieInfo()
    var existingMovieId: String = ""
    var videoFilename: String? = ""

    fun generatePoster(id: String, dirName: String, chosenBackgroundImage: BufferedImage? = null): Triple<BufferedImage, String, String> {

        if (id != existingMovieId) {
            videoFilename = movieManager.nasService.getVideoFilename(dirName)
            if (videoFilename == null) {
                return Triple(EMPTY_IMAGE, "", "")
            }
            movieInfo = movieManager.tmdbReader.getMovieInfo(id)
            videoInfo = getMovieMetadata(movieManager.config.ffprobePath, "${movieManager.config.moviesDir}$dirName$videoFilename")
            cover = if (movieInfo.posterPath != null) Thumbnails.of(movieManager.tmdbReader.getSmallSizePoster(movieInfo.posterPath!!)).forceSize(154, 231).asBufferedImage() else EMPTY_IMAGE
        }
        existingMovieId = id
        val generator = Generator(movieInfo, videoInfo, template)
        val backgroundImage = getBackgroundImage(chosenBackgroundImage)

        val generatedImage = generator.generate(backgroundImage, cover)
        return Triple(generatedImage, movieInfo.toXml(), movieInfo.posterPath ?: "")
    }

    private fun getBackgroundImage(chosenBackgroundImage: BufferedImage?): BufferedImage {
        if (chosenBackgroundImage != null) {
            return chosenBackgroundImage
        } else if (movieInfo.backdropPath != null) {
            return Thumbnails.of(movieManager.tmdbReader.getOriginalSizeImage(movieInfo.backdropPath!!))
                .forceSize(1920, 1080).asBufferedImage()
        }
        return EMPTY_IMAGE
    }

}