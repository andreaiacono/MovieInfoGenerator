package me.andreaiacono.generator.gui

import me.andreaiacono.generator.service.MovieManager
import java.awt.Cursor
import javax.swing.*
import javax.swing.ListSelectionModel


class ExistingMoviesPanel(val movieManager: MovieManager) : JPanel() {

    val existingPostersModel = DefaultListModel<String>()
    var selected = -1

    init {
        val posterViewer = PosterViewer()
        val existingMoviesList = JList(existingPostersModel)
        existingMoviesList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        existingMoviesList.addListSelectionListener {
            if (existingMoviesList.selectedIndex != selected) {
                selected = existingMoviesList.selectedIndex
                cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                try {
                    val dirName = (existingPostersModel[existingMoviesList.selectedIndex])
                    posterViewer.setPoster(movieManager.getMoviePoster(dirName))
                } finally {
                    cursor = Cursor.getDefaultCursor()
                }
            }
        }
        val scrollableExistingMovieList = JScrollPane(existingMoviesList)
        val spDivider = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollableExistingMovieList, posterViewer)

        val sl = SpringLayout()
        layout = sl

        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)

        add(spDivider)
    }
}