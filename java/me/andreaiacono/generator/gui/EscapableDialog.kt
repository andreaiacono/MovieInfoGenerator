package me.andreaiacono.generator.gui

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JRootPane
import javax.swing.KeyStroke

open class EscapableDialog : JDialog() {

    override fun createRootPane(): JRootPane {
        val actionListener = { _: ActionEvent -> isVisible = false }
        val rootPane = JRootPane()
        val stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
        rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW)
        return rootPane
    }
}