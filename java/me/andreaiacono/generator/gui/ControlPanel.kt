package me.andreaiacono.generator.gui

import javax.swing.*

class ControlPanel : JPanel() {

    private val textArea: JTextArea

    init {
        val sl = SpringLayout()
        layout = sl

        val saveButton = JButton("Save")
        saveButton.addActionListener {
            println("save!")
        }
        add(saveButton)

        sl.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, this)

        textArea = JTextArea ()
        val scrollableTextArea = JScrollPane(textArea)
        add(scrollableTextArea)

        sl.putConstraint(SpringLayout.NORTH, scrollableTextArea, 5, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, scrollableTextArea, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, scrollableTextArea, -5, SpringLayout.WEST, saveButton)
        sl.putConstraint(SpringLayout.WEST, scrollableTextArea, 5, SpringLayout.WEST, this)
    }

    fun setXml(xml: String) {
        textArea.text = xml
    }

}
