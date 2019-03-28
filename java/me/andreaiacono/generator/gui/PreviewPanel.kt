package me.andreaiacono.generator.gui

import java.awt.image.BufferedImage
import javax.swing.*

class PreviewPanel : JPanel() {

    private val posterViewer: PosterViewer = PosterViewer()
    private val controlPanel: ControlPanel = ControlPanel()

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
}
