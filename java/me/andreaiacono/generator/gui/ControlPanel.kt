package me.andreaiacono.generator.gui

import javax.swing.*

class ControlPanel(previewPanel: PreviewPanel) : JPanel() {

    private val textArea: JTextArea = JTextArea ()

    init {
        val sl = SpringLayout()
        layout = sl

        val imagesPanel = JPanel()
        val scrollableImages = JScrollPane(imagesPanel)
        add(scrollableImages)

        val scrollableTextArea = JScrollPane(textArea)
        add(scrollableTextArea)

        val saveButton = JButton("Save")
        saveButton.addActionListener {
            previewPanel.save(textArea.text)
        }
        add(saveButton)

        sl.putConstraint(SpringLayout.NORTH, scrollableImages, 5, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, scrollableImages, 250, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.EAST, scrollableImages, -5, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, scrollableImages, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.NORTH, scrollableTextArea, 5, SpringLayout.SOUTH, scrollableImages)
        sl.putConstraint(SpringLayout.SOUTH, scrollableTextArea, -5, SpringLayout.NORTH, saveButton)
        sl.putConstraint(SpringLayout.EAST, scrollableTextArea, -5, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, scrollableTextArea, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, this)
    }

    fun setXml(xml: String) {
        textArea.text = xml
    }

}
