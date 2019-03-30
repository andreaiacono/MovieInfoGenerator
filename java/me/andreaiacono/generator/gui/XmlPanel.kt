package me.andreaiacono.generator.gui

import me.andreaiacono.generator.util.runAsync
import javax.swing.*

class XmlPanel(val previewPanel: PreviewPanel) : JPanel() {

    private val textArea: JTextArea = JTextArea()

    init {
        val sl = SpringLayout()
        layout = sl

        val scrollableTextArea = JScrollPane(textArea)
        add(scrollableTextArea)

        val saveButton = JButton("Save")
        saveButton.addActionListener {
            runAsync(this@XmlPanel, Runnable {
                previewPanel.save(textArea.text)
            })
        }
        add(saveButton)

        sl.putConstraint(SpringLayout.NORTH, scrollableTextArea, 5, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, scrollableTextArea, -5, SpringLayout.NORTH, saveButton)
        sl.putConstraint(SpringLayout.EAST, scrollableTextArea, -5, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, scrollableTextArea, 5, SpringLayout.WEST, this)

        sl.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, this)
    }

    fun setXml(xml: String) {
        textArea.text = xml
    }

    fun clear() {
        textArea.text = ""
        updateUI()
    }
}
