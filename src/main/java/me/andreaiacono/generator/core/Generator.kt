package me.andreaiacono.generator

import me.andreaiacono.generator.model.TmdbMovieInfo
import java.awt.*
import java.awt.image.BufferedImage
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.text.AttributedString
import java.awt.Insets


class Generator(val movie: TmdbMovieInfo, val videoInfo: String, val template: Template) {

    private val MAX_SYNOPSIS_LENGTH = 1050
    val WIDTH = 1920
    val HEIGHT = 1341

    private val TITLE_FONT = Font("Din Condensed", Font.BOLD, 60)
//    private val TITLE_FONT = Font("Yanone Kaffeesatz", Font.BOLD, 60)
    private val TITLE_COLOR = Color(140, 180, 180, 200)

    private val SYNOPSIS_FONT = Font("Gill Sans", Font.BOLD, 28)
//    private val SYNOPSIS_FONT = Font("Linux Biolinum", Font.PLAIN, 27)
    private val SYNOPSIS_COLOR = Color(180, 220, 220, 200)

//    private val INFO_FONT = Font("Linux Biolinum", Font.PLAIN, 24)
    private val INFO_FONT = Font("Gill Sans", Font.PLAIN, 24)
    private val INFO_COLOR = SYNOPSIS_COLOR

    fun generate(background: BufferedImage, cover: BufferedImage): BufferedImage {
        val poster = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
        val g = poster.createGraphics()
        g.color = Color.DARK_GRAY
        g.fillRect(0, 0, WIDTH, HEIGHT)

        var colTop = 1065 + 20
        g.drawImage(background, 0, 0, null)
//        g.drawImage(background, 0, 80, null)
        g.drawImage(template.frame, 0, 0, null)
        g.drawImage(cover, 14, colTop, null)

        // draw title
        val title = "${movie.title?.toUpperCase()}  [${movie.releaseDate?.take(4)}]"
        g.font = TITLE_FONT
        g.color = TITLE_COLOR
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.drawString(title, computeLeftForCenter(background.width, title, g), (g.font.size*1.055).toInt())

        val colLeft = 1653

        // draws synopsis
        val insets =  Insets(colTop, 184, 1100, colLeft - 60)
        drawSynopsys(insets, g)

        // draw movie info
        g.font = INFO_FONT
        g.color = INFO_COLOR
        colTop += 5
        colTop += (g.font.size * 0.7).toInt()
        g.drawString(videoInfo, colLeft, colTop)
        colTop += (g.font.size * 1.4).toInt()
        g.drawString(minutesToHoursString(movie.runtime ?: 0), colLeft, colTop)
        colTop += (g.font.size*1.5).toInt()
        g.drawString(movie.getDirectors().joinToString(), colLeft, colTop)
        colTop += (g.font.size*1.6).toInt()
        movie.getActors(5).forEach{
            g.drawString(it, colLeft, colTop)
            colTop += (g.font.size*1.20).toInt()
        }

        return poster
    }

    private fun drawSynopsys(insets: Insets, g: Graphics2D) {
        val overview: String = movie.overview.orEmpty().take(500)
        g.font = SYNOPSIS_FONT
        g.font = Font("Gill Sans", Font.PLAIN, 37 - (overview.length / 40))
        g.color = SYNOPSIS_COLOR
        val frc = g.fontRenderContext
        val synopsis = if (overview.length < MAX_SYNOPSIS_LENGTH) movie.overview else overview.take(MAX_SYNOPSIS_LENGTH) + "[...]"
        val messageAS = AttributedString("$synopsis ")
        messageAS.addAttribute(TextAttribute.FONT, g.font)
        val messageIterator = messageAS.iterator
        val measurer = LineBreakMeasurer(messageIterator, frc)
        val wrappingWidth = (insets.right - insets.left).toFloat()
        var y = insets.top.toFloat()
        var x = insets.left.toFloat()
        while (measurer.position < messageIterator.endIndex) {
            val textLayout = measurer.nextLayout(wrappingWidth)
            y += textLayout.ascent.toInt() + 4
            textLayout.draw(g, x, y)
            y += textLayout.descent.toInt() + textLayout.leading.toInt()
            x = insets.left.toFloat()
        }
    }

    private fun computeLeftForCenter(width: Int, text: String, g: Graphics) = (width - g.fontMetrics.stringWidth(text)) / 2

    private fun minutesToHoursString(minutes: Int): String = String.format("%dh%02dm", minutes / 60, minutes % 60)
}

