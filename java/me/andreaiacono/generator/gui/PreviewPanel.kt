package me.andreaiacono.generator.gui

import me.andreaiacono.generator.service.MovieManager

import java.awt.image.BufferedImage
import javax.swing.*
class PreviewPanel(val movieManager: MovieManager) : JPanel() {

    private val posterViewer: PosterViewer = PosterViewer()
    private val controlPanel: ControlPanel = ControlPanel(this)
    private lateinit var dirName: String
    private lateinit var id: String
    private lateinit var coverUri: String

    init {
        val spDivider = JSplitPane(JSplitPane.VERTICAL_SPLIT, posterViewer, controlPanel)
        spDivider.dividerLocation = 200
        add(spDivider)

        val sl = SpringLayout()
        layout = sl
        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)
    }

    fun setPoster(image: BufferedImage) {
        posterViewer.image = image
        posterViewer.repaint()
    }

    fun setXml(xml: String) {
        controlPanel.setXml(xml)
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
}
