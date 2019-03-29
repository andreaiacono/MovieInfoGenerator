package me.andreaiacono.generator.gui

import me.andreaiacono.generator.service.MovieManager
import me.andreaiacono.generator.util.runAsync
import java.awt.FlowLayout
import java.awt.event.MouseAdapter
import java.awt.image.BufferedImage
import java.awt.event.MouseEvent
import javax.swing.*


class AlternateImagesPanel(val previewPanel: PreviewPanel) : JPanel() {

    val images = mutableListOf<BufferedImage>()

    private var imagePanel: JPanel

    private val scrollPane: JScrollPane

    init {
        imagePanel = JPanel()
        imagePanel.layout = FlowLayout()
        scrollPane = JScrollPane(imagePanel)
        add(scrollPane)

        val sl = SpringLayout()
        layout = sl
        sl.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this)
    }

    fun loadImages(movieId: String, movieManager: MovieManager, dirName: String) {
        runAsync(this, Runnable {
            val results = movieManager.getAlternativeImages(movieId)
            results.backdrops?.forEach {
                val image = movieManager.getAlternativeImageThumb(it?.filePath)
                images.add(image)
                val imageLabel = JLabel(ImageIcon(image))
                imagePanel.add(imageLabel)
                updateUI()
                imageLabel.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        if (e.button == MouseEvent.BUTTON1) {
                            runAsync(scrollPane, Runnable {
                                val fullsizeImage = movieManager.getAlternativeImageFullsize(it?.filePath)
                                val (poster, xml, coverUri) = movieManager.generatePoster(movieId, dirName, fullsizeImage)
                                previewPanel.setPoster(poster)
                                previewPanel.setXml(xml)
                                previewPanel.setCoverUri(coverUri)
                            })
                        }
                    }
                })
            }
        })
    }

    fun clear() {
        images.clear()
        imagePanel.removeAll()
        updateUI()
    }
}
