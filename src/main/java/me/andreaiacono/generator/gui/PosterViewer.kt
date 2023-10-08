package me.andreaiacono.generator.gui

import net.coobird.thumbnailator.Thumbnails
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.JPanel

class PosterViewer : JPanel() {

    var image: BufferedImage? = null
    var isDisplayed = false

    override fun paint(g: Graphics?) {
        super.paint(g)
        if (image != null) {
            g?.drawImage(Thumbnails.of(image).size(width, height).asBufferedImage(), 0, 0, this)
        }
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.button == MouseEvent.BUTTON1 && image != null && ! isDisplayed) {
                    isDisplayed = true
                    val preview = PreviewFrame(image!!)
                    preview.isVisible = true
                }
            }
        })
    }

    fun setPoster(image: BufferedImage) {
        this.image = image
        invalidate()
        repaint()
    }

    fun clear() {
        this.image = null
        invalidate()
        repaint()
        isDisplayed = false
    }
}