package me.andreaiacono.generator.gui

import javax.swing.*

class ControlPanel(previewPanel: PreviewPanel) : JPanel() {

    private val textArea: JTextArea = JTextArea ()

    init {
        val sl = SpringLayout()
        layout = sl

        val scrollableTextArea = JScrollPane(textArea)
        add(scrollableTextArea)

        val saveButton = JButton("Save")
        saveButton.addActionListener {
            previewPanel.save(textArea.text)
        }
        add(saveButton)

        sl.putConstraint(SpringLayout.NORTH, scrollableTextArea, 5, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, scrollableTextArea, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, scrollableTextArea, -5, SpringLayout.WEST, saveButton)
        sl.putConstraint(SpringLayout.WEST, scrollableTextArea, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, this)
    }

    fun setXml(xml: String) {
        textArea.text = xml
    }

}
