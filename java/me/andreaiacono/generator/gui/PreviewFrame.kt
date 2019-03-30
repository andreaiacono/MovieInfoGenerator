package me.andreaiacono.generator.gui

import net.coobird.thumbnailator.Thumbnails
import java.awt.Component
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SpringLayout

class PreviewFrame(var image: BufferedImage): JFrame() {

    init {
        setSize(1200, 800)
        val imageLabel = JLabel(ImageIcon(image))
        add(imageLabel)
        val sl = SpringLayout()
        sl.putConstraint(SpringLayout.NORTH, imageLabel, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, imageLabel, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, imageLabel, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, imageLabel, 0, SpringLayout.WEST, this)

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(evt: ComponentEvent?) {
                val c = evt!!.source as Component
                imageLabel.icon = ImageIcon(Thumbnails.of(image).size(c.width, c.height).asBufferedImage())
            }
        })
    }
}