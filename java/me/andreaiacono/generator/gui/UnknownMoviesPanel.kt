package me.andreaiacono.generator.gui

import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane

class UnknownMoviesPanel : JPanel() {

    val unknownPostersModel = DefaultListModel<String>()

    init {
        val notExistingMoviesList = JList(unknownPostersModel)
        val scrollableNotExistingMovieList = JScrollPane(notExistingMoviesList)
        add(scrollableNotExistingMovieList)
    }
}