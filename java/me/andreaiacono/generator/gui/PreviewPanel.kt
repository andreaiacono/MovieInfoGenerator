package me.andreaiacono.generator.gui

import me.andreaiacono.generator.service.MovieManager
import java.awt.image.BufferedImage
import javax.swing.*

class PreviewPanel(val movieManager: MovieManager) : JPanel() {

    private val posterViewer: PosterViewer = PosterViewer()
    private val xmlPanel: XmlPanel = XmlPanel(this)
    private lateinit var dirName: String
    private lateinit var id: String
    private lateinit var coverUri: String

    private val imagesPanel: AlternateImagesPanel

    init {

        imagesPanel = AlternateImagesPanel(this)
        add(imagesPanel)

        val spDivider = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, posterViewer, xmlPanel)
        spDivider.dividerLocation = 700
        add(spDivider)

        val sl = SpringLayout()
        layout = sl

        sl.putConstraint(SpringLayout.NORTH, imagesPanel, -205, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.SOUTH, imagesPanel, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, imagesPanel, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, imagesPanel, 0, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 5, SpringLayout.NORTH, imagesPanel)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)
    }

    fun setPoster(image: BufferedImage) {
        posterViewer.image = image
        posterViewer.repaint()
    }

    fun setXml(xml: String) {
        xmlPanel.setXml(xml)
    }

    fun save(xml: String) {
        movieManager.saveData(posterViewer.image, xml, dirName, id, coverUri)
    }

    fun setDirname(dirName: String) {
        this.dirName = dirName
    }

    fun setId(id: String) {
        this.id = id
    }

    fun setCoverUri(coverUri: String) {
        this.coverUri = coverUri
    }

    fun loadAlternateImages(movieId: String, dirName: String) {
        imagesPanel.loadImages(movieId, movieManager, dirName)
    }

    fun clear() {
        imagesPanel.clear()
        xmlPanel.clear()
    }
}
