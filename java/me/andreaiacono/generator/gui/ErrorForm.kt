package me.andreaiacono.generator.gui

import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.PrintWriter
import java.io.StringWriter
import javax.swing.*

class ErrorForm(ex: Exception) : EscapableDialog(), ActionListener {

    private val pane: JScrollPane
    private val jbDetails: JButton

    init {
        title = "Error Message"
        isModal = true
        setSize(1000, 100)
        isResizable = true

        val message = if (ex.message == null) "NullPointerException" else ex.message
        val sw = StringWriter()
        ex.printStackTrace(PrintWriter(sw))

        val jlMessage = JLabel("An error has occurred: $message", JLabel.CENTER)
        jlMessage.iconTextGap = 10
        val icon = UIManager.getLookAndFeel().defaults["OptionPane.errorIcon"] as Icon
        jlMessage.icon = icon
        jlMessage.preferredSize = Dimension(100, 40)

        val jtaText = JTextArea(10, 50)
        pane = JScrollPane(jtaText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS)
        jtaText.isEditable = false
        pane.isVisible = false

        // prints the error on the textarea
        jtaText.append("\n\nERROR: $message")
        jtaText.append("\n\n$sw")
        jtaText.caretPosition = 0

        val jbClose = JButton("Close")
        jbClose.addActionListener(this)

        jbDetails = JButton("Show Details")
        jbDetails.addActionListener(this)

        val sl = SpringLayout()
        layout = sl

        sl.putConstraint(SpringLayout.WEST, jlMessage, 10, SpringLayout.WEST, this.contentPane)
        sl.putConstraint(SpringLayout.NORTH, jlMessage, 5, SpringLayout.NORTH, this.contentPane)
        sl.putConstraint(SpringLayout.EAST, jlMessage, -5, SpringLayout.EAST, this.contentPane)

        sl.putConstraint(SpringLayout.WEST, pane, 5, SpringLayout.WEST, this.contentPane)
        sl.putConstraint(SpringLayout.NORTH, pane, 5, SpringLayout.SOUTH, jlMessage)
        sl.putConstraint(SpringLayout.EAST, pane, -5, SpringLayout.EAST, this.contentPane)
        sl.putConstraint(SpringLayout.SOUTH, pane, -5, SpringLayout.NORTH, jbClose)

        sl.putConstraint(SpringLayout.EAST, jbClose, -5, SpringLayout.EAST, this.contentPane)
        sl.putConstraint(SpringLayout.SOUTH, jbClose, -5, SpringLayout.SOUTH, this.contentPane)

        sl.putConstraint(SpringLayout.WEST, jbDetails, 5, SpringLayout.WEST, this.contentPane)
        sl.putConstraint(SpringLayout.SOUTH, jbDetails, -5, SpringLayout.SOUTH, this.contentPane)

        add(pane)
        add(jbClose)
        add(jbDetails)
        add(jlMessage)
    }

    override fun actionPerformed(e: ActionEvent) {
        when {
            e.actionCommand == "Close" -> this.dispose()
            e.actionCommand == "Show Details" -> changeDetails(false)
            e.actionCommand == "Hide Details" -> changeDetails(true)
        }
    }

    private fun changeDetails(isToHide: Boolean) {
        val height = if (isToHide) 100 else 500
        val buttonLabel = if (isToHide) "Show" else "Hide"
        this.setSize(1000, height)
        pane.isVisible = !pane.isVisible
        jbDetails.text = "$buttonLabel Details"
        repaint()
    }
}