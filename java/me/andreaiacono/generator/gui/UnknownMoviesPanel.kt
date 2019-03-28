package me.andreaiacono.generator.gui

import me.andreaiacono.generator.gui.util.ErrorForm
import me.andreaiacono.generator.service.MovieManager
import java.awt.Cursor
import javax.swing.*

class UnknownMoviesPanel(val movieManager: MovieManager) : JPanel() {

    val movieListModel = DefaultListModel<String>()
    val searchResultsListModel = DefaultListModel<String>()
    var selectedMovie = -1
    var selectedResult = -1

    private var titleInput: JTextField
    private var submitTitle: JButton

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

        titleInput = JTextField()
        submitTitle = JButton("Search")
        submitTitle.addActionListener { searchMovie() }

        leftPanel.add(titleInput)
        leftPanel.add(submitTitle)

        val searchResultLabel = JLabel("Search result:")
        leftPanel.add(searchResultLabel)

        lsl.putConstraint(SpringLayout.NORTH, unknownMoviesLabel, 5, SpringLayout.NORTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, unknownMoviesLabel, 5, SpringLayout.WEST, leftPanel)

        lsl.putConstraint(SpringLayout.NORTH, scrollableNotExistingMovieList, 5, SpringLayout.SOUTH, unknownMoviesLabel)
        lsl.putConstraint(SpringLayout.SOUTH, scrollableNotExistingMovieList, 180, SpringLayout.NORTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, scrollableNotExistingMovieList, 5, SpringLayout.WEST, leftPanel)
        lsl.putConstraint(SpringLayout.EAST, scrollableNotExistingMovieList, -5, SpringLayout.EAST, leftPanel)

        lsl.putConstraint(SpringLayout.NORTH, submitTitle, 5, SpringLayout.SOUTH, scrollableNotExistingMovieList)
        lsl.putConstraint(SpringLayout.EAST, submitTitle, -5, SpringLayout.EAST, leftPanel)

        lsl.putConstraint(SpringLayout.NORTH, titleInput, 5, SpringLayout.SOUTH, scrollableNotExistingMovieList)
        lsl.putConstraint(SpringLayout.WEST, titleInput, 5, SpringLayout.WEST, leftPanel)
        lsl.putConstraint(SpringLayout.EAST, titleInput, 0, SpringLayout.WEST, submitTitle)

        lsl.putConstraint(SpringLayout.NORTH, searchResultLabel, 15, SpringLayout.SOUTH, titleInput)
        lsl.putConstraint(SpringLayout.WEST, searchResultLabel, 5, SpringLayout.WEST, this)

        lsl.putConstraint(SpringLayout.NORTH, scrollableSearchResultsList, 5, SpringLayout.SOUTH, searchResultLabel)
        lsl.putConstraint(SpringLayout.SOUTH, scrollableSearchResultsList, -5, SpringLayout.SOUTH, leftPanel)
        lsl.putConstraint(SpringLayout.WEST, scrollableSearchResultsList, 5, SpringLayout.WEST, leftPanel)
        lsl.putConstraint(SpringLayout.EAST, scrollableSearchResultsList, -5, SpringLayout.EAST, leftPanel)


        val previewPanel = PreviewPanel()
        val spDivider = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, previewPanel)
        spDivider.dividerLocation = 200

        val sl = SpringLayout()
        layout = sl
        sl.putConstraint(SpringLayout.NORTH, spDivider, 0, SpringLayout.NORTH, this)
        sl.putConstraint(SpringLayout.SOUTH, spDivider, 0, SpringLayout.SOUTH, this)
        sl.putConstraint(SpringLayout.EAST, spDivider, 0, SpringLayout.EAST, this)
        sl.putConstraint(SpringLayout.WEST, spDivider, 0, SpringLayout.WEST, this)

        unknownMoviesList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        unknownMoviesList.addListSelectionListener {
            if (unknownMoviesList.selectedIndex != selectedMovie) {
                selectedMovie = unknownMoviesList.selectedIndex
                titleInput.text = movieListModel[selectedMovie]
                searchMovie()
            }
        }

        searchResultsList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        searchResultsList.addListSelectionListener {
            if (searchResultsList.selectedIndex != selectedResult) {
                selectedResult = searchResultsList.selectedIndex
                cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)

                try {
                    val id = (searchResultsListModel[searchResultsList.selectedIndex].substringAfterLast(" "))
                    val (image, xml) = movieManager.generatePoster(id, movieListModel[unknownMoviesList.selectedIndex] + "/")
                    previewPanel.setPoster(image)
                    previewPanel.setXml(xml)

                } catch (ex: Exception) {
                    ErrorForm(ex).isVisible = true
                } finally {
                    cursor = Cursor.getDefaultCursor()
                }
            }
        }
        add(spDivider)
    }

    private fun searchMovie() {
        cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
        try {
            val title = titleInput.text
            val results = movieManager.searchMovie(title)
            searchResultsListModel.removeAllElements()
            searchResultsListModel.addAll(results)
        } catch (ex: Exception) {
            ErrorForm(ex).isVisible = true
        } finally {
            cursor = Cursor.getDefaultCursor()
        }
    }

    fun reloadData(movies: List<String>) {
        movieListModel.removeAllElements()
        movieListModel.addAll(movies)
    }
}