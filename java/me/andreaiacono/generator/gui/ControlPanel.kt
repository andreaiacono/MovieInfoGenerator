package me.andreaiacono.generator.gui

import me.andreaiacono.generator.util.runAsync
import javax.swing.*

class ControlPanel(val previewPanel: PreviewPanel) : JPanel() {

    private val textArea: JTextArea = JTextArea()
    private val imagesPanel: AlternateImagesPanel

    init {
        val sl = SpringLayout()
        layout = sl

        imagesPanel = AlternateImagesPanel(previewPanel)
        add(imagesPanel)

        val scrollableTextArea = JScrollPane(textArea)
        add(scrollableTextArea)

        val saveButton = JButton("Save")
        saveButton.addActionListener {
            runAsync(this@ControlPanel, Runnable {
                previewPanel.save(textArea.text)
            })
        }
        add(saveButton)

        sl.putConstraint(SpringLayout.NORTH, imagesPanel, 5, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, imagesPanel, 205, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.EAST, imagesPanel, -5, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, imagesPanel, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.NORTH, scrollableTextArea, 5, SpringLayout.SOUTH, imagesPanel)
        sl.putConstraint(SpringLayout.SOUTH, scrollableTextArea, -5, SpringLayout.NORTH, saveButton)
        sl.putConstraint(SpringLayout.EAST, scrollableTextArea, -5, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, scrollableTextArea, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, this)
    }

    fun setXml(xml: String) {
        textArea.text = xml
    }

    fun loadImages(id: String, dirName: String) {
        imagesPanel.loadImages(id, previewPanel.movieManager, dirName)
    }

    fun clear() {
        imagesPanel.clear()
        textArea.text = ""
        updateUI()
    }
}
