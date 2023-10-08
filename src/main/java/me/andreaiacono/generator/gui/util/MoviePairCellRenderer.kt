package me.andreaiacono.generator.gui.util

import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JList

class MoviePairCellRenderer : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(list: JList<*>?, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
        text = (value as Pair<String, String>).first
        return this
    }
}