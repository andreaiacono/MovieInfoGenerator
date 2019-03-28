package me.andreaiacono.generator.gui

import me.andreaiacono.generator.service.MovieManager
import java.awt.Cursor
import javax.swing.*

class UnknownMoviesPanel(movieManager: MovieManager) : JPanel() {

    val movieListModel = DefaultListModel<String>()
    val searchResultsListModel = DefaultListModel<String>()
    var selectedMovie = -1
    var selectedResult = -1

    init {

        val leftPanel = JPanel()
        val lsl = SpringLayout()
        leftPanel.layout = lsl

        val unknownMoviesList = JList(movieListModel)
        val scrollableNotExistingMovieList = JScrollPane(unknownMoviesList)
        leftPanel.add(scrollableNotExistingMovieList)

        val unknownMoviesLabel = JLabel("Unknown movies:")
        leftPanel.add(unknownMoviesLabel)

        val searchResultsList = JList(searchResultsListModel)
        val scrollableSearchResultsList = JScrollPane(searchResultsList)
        leftPanel.add(scrollableSearchResultsList)

        val searchResultLabel = JLabel("Search result:")
        leftPanel.add(searchResultLabel)

        lsl.putConstraint(SpringLayout.NORTH, unknownMoviesLabel, 5, SpringLayout.NORTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, unknownMoviesLabel, 5, SpringLayout.WEST, leftPanel)

        lsl.putConstraint(SpringLayout.NORTH, scrollableNotExistingMovieList, 5, SpringLayout.SOUTH, unknownMoviesLabel)
        lsl.putConstraint(SpringLayout.SOUTH, scrollableNotExistingMovieList, 180, SpringLayout.NORTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, scrollableNotExistingMovieList, 5, SpringLayout.WEST, leftPanel)
        lsl.putConstraint(SpringLayout.EAST, scrollableNotExistingMovieList, -5, SpringLayout.EAST, leftPanel)

        lsl.putConstraint(SpringLayout.NORTH, searchResultLabel, 15, SpringLayout.SOUTH, scrollableNotExistingMovieList)
        lsl.putConstraint(SpringLayout.WEST, searchResultLabel, 5, SpringLayout.WEST, this)

        lsl.putConstraint(SpringLayout.NORTH, scrollableSearchResultsList, 5, SpringLayout.SOUTH, searchResultLabel)
        lsl.putConstraint(SpringLayout.SOUTH, scrollableSearchResultsList, -5, SpringLayout.SOUTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, scrollableSearchResultsList, 5, SpringLayout.WEST, leftPanel)
        lsl.putConstraint(SpringLayout.EAST, scrollableSearchResultsList, -5, SpringLayout.EAST, leftPanel)


        val posterViewer = PosterViewer()
        val spDivider = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, posterViewer)

        val sl = SpringLayout()
        layout = sl
        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)

        add(spDivider)


        unknownMoviesList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        unknownMoviesList.addListSelectionListener {
            if (unknownMoviesList.selectedIndex != selectedMovie) {
                selectedMovie = unknownMoviesList.selectedIndex
                println("seleected: $selectedMovie")
                cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)

                try {
                    val dirName = movieListModel[selectedMovie]
                    println("dirname=$dirName")

                    val results = movieManager.searchMovie(dirName)
                    searchResultsListModel.removeAllElements()
                    searchResultsListModel.addAll(results)
                }
                catch (ex: Exception) {
                    ErrorForm(ex).isVisible = true
                }
                finally {
                    cursor = Cursor.getDefaultCursor()
                }
            }
        }

        searchResultsList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        searchResultsList.addListSelectionListener {
            if (searchResultsList.selectedIndex != selectedResult) {
                selectedResult = searchResultsList.selectedIndex
                println("seleected: $selectedResult")
                cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)

                try {
                    val id = (searchResultsListModel[searchResultsList.selectedIndex].substringAfterLast(" "))
                    println("id=$id")
                    val image = movieManager.generatePoster(id, movieListModel[unknownMoviesList.selectedIndex])
                    posterViewer.setPoster(image)
                }
                finally {
                    cursor = Cursor.getDefaultCursor()
                }
            }
        }
    }

    fun reloadData(movies: List<String>) {
        movieListModel.removeAllElements()
        movieListModel.addAll(movies)
    }
}