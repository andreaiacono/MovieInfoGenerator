package me.andreaiacono.generator

import me.andreaiacono.generator.model.Movie
import java.awt.*
import java.awt.image.BufferedImage
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.text.AttributedString
import java.awt.Insets


class Generator(val movie: Movie, val template: Template) {

    private val MAX_SYNOPSIS_LENGTH = 850

    private val TITLE_FONT = Font("Din Condensed", Font.BOLD, 60)
    private val TITLE_COLOR = Color(140, 180, 180, 200)

    private val SYNOPSIS_FONT = Font("Gill Sans", Font.PLAIN, 27)
    private val SYNOPSIS_COLOR = Color(180, 220, 220, 200)

    private val INFO_FONT = Font("Gill Sans", Font.PLAIN, 24)
    private val INFO_COLOR = SYNOPSIS_COLOR

    fun generate(background: BufferedImage, cover: BufferedImage): BufferedImage {
        val g = background.graphics as Graphics2D
        val frame: BufferedImage = template.frame
        g.drawImage(frame, 0, 0, null)
//        val cover = createCover(movie.resolution)
        g.drawImage(cover, 14, 834, null)

        // draw title
        g.font = TITLE_FONT
        g.color = TITLE_COLOR
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.drawString(movie.title.toUpperCase(), computeLeftForCenter(background.width, movie.title, g), (g.font.size*1.055).toInt())

        val colLeft = 1653
        var colTop = 830

        // draws synopsis
        val insets =  Insets(colTop, 184, 1100, colLeft - 60)
        drawSynopsys(insets, g)

        // draw time
        g.font = INFO_FONT
        g.color = INFO_COLOR
        colTop += (g.font.size * 0.7).toInt()
        g.drawString(movie.resolution, colLeft, colTop)
        colTop += (g.font.size * 1.4).toInt()
        g.drawString(minutesToHoursString(movie.duration!!), colLeft, colTop)
        colTop += (g.font.size*1.5).toInt()
        g.drawString(movie.directors.take(5).joinToString(), colLeft, colTop)
        colTop += (g.font.size*1.6).toInt()
        movie.cast.forEach{
            g.drawString(it, colLeft, colTop)
            colTop += (g.font.size*1.20).toInt()
        }

        return background
    }

    fun drawSynopsys(insets: Insets, g: Graphics2D) {
        g.font = SYNOPSIS_FONT
        g.font = Font("Gill Sans", Font.PLAIN, 35 - (movie.synopsis.length / 100))
        g.color = SYNOPSIS_COLOR
        val frc = g.fontRenderContext
        val synopsis = if (movie.synopsis.length < MAX_SYNOPSIS_LENGTH) movie.synopsis else movie.synopsis.take(MAX_SYNOPSIS_LENGTH) + "[...]"
        val messageAS = AttributedString(synopsis)
        messageAS.addAttribute(TextAttribute.FONT, g.font)
        val messageIterator = messageAS.iterator
        val measurer = LineBreakMeasurer(messageIterator, frc)
        val wrappingWidth = (insets.right - insets.left).toFloat()
        var y = insets.top.toFloat()
        var x = insets.left.toFloat()
        while (measurer.position < messageIterator.endIndex) {
            val textLayout = measurer.nextLayout(wrappingWidth)
            y += textLayout.ascent.toInt()
            textLayout.draw(g, x, y)
            y += textLayout.descent.toInt() + textLayout.leading.toInt()
            x = insets.left.toFloat()
        }

    }

    private fun computeLeftForCenter(width: Int, text: String, g: Graphics) = (width - g.fontMetrics.stringWidth(text)) / 2

//    fun createCover(res: MovieResolution): BufferedImage {
//        val case = when (res) {
//            MovieResolution.BLURAY -> "bluray.png"
//            MovieResolution.DVD -> "alt_dvd.png"
//            MovieResolution.ULTRA_HD -> "ultrahd.png"
//            MovieResolution.LOW_RES -> "lowres.png"
//        }
//        val caseImage =  ImageIO.read(File(Generator::class.java.getResource("/template/$case").file))
//        val g = caseImage.graphics
//        g.drawImage(Thumbnails.of(cover).forceSize(298, 445).asBufferedImage(), 0, 45, null)
//
//        return caseImage
//    }

    fun minutesToHoursString(minutes: Int): String = String.format("%dh%02dm", minutes / 60, minutes % 60)
}

