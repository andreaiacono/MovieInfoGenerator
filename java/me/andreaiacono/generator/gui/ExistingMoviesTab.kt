package me.andreaiacono.generator.gui

import me.andreaiacono.generator.gui.util.ErrorForm
import me.andreaiacono.generator.gui.util.MoviePairCellRenderer
import me.andreaiacono.generator.service.MovieManager
import java.awt.Cursor
import javax.swing.*
import javax.swing.ListSelectionModel
import javax.swing.JList

class ExistingMoviesTab(val movieManager: MovieManager) : JPanel() {


    private val listModel = DefaultListModel<Pair<String, String>>()
    private var selected = -1

    init {
        val posterViewer = PosterViewer()
        val existingMoviesList = JList(listModel)

        existingMoviesList.model = listModel
        existingMoviesList.cellRenderer = MoviePairCellRenderer()
        existingMoviesList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        existingMoviesList.addListSelectionListener {
            if (existingMoviesList.selectedIndex != selected) {
                selected = existingMoviesList.selectedIndex
                cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                try {
                    val dirName = (listModel[existingMoviesList.selectedIndex])
                    posterViewer.setPoster(movieManager.getMoviePoster(dirName.second))
                }
                catch (ex: Exception) {
                    ErrorForm(ex).isVisible = true
                }
                finally {
                    cursor = Cursor.getDefaultCursor()
                }
            }
        }
        val scrollableExistingMovieList = JScrollPane(existingMoviesList)
        val spDivider = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollableExistingMovieList, posterViewer)
        spDivider.dividerLocation = 200

        val sl = SpringLayout()
        layout = sl

        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)

        add(spDivider)
    }

    fun reloadData(movies: List<Pair<String, String>>) {
        listModel.removeAllElements()
        movies.forEach { listModel.addElement(it) }
    }
}
