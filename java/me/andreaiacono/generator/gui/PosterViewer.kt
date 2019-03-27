package me.andreaiacono.generator.gui

import net.coobird.thumbnailator.Thumbnails
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JPanel

class PosterViewer : JPanel() {

    var image: BufferedImage? = null

    override fun paint(g: Graphics?) {
        super.paintComponent(g)
        if (image != null) {
            g?.drawImage(Thumbnails.of(image).size(width, height).asBufferedImage(), 0, 0, this)
        }
    }

    fun setPoster(image: BufferedImage) {
        this.image = image
        repaint()
    }
}