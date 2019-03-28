package me.andreaiacono.generator.gui.util

import java.awt.Component
import java.awt.Font
import javax.swing.DefaultListCellRenderer
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JList

class MovieChooserDialog : JDialog() {

    init {

    }
}

class MovieItemChoserRenderer : DefaultListCellRenderer() {

    internal var font = Font("helvetica", Font.BOLD, 24)

    override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {

        val label = super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus
        ) as JLabel
//        label.icon = imageMap.get(value as String?)
        label.horizontalTextPosition = JLabel.RIGHT
        label.font = font
        return label
    }
}
