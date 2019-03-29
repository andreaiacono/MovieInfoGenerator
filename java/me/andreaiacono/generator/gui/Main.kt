package me.andreaiacono.generator.gui

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.service.MovieManager
import me.andreaiacono.generator.util.runAsync
import java.awt.*
import java.io.File
import javax.swing.*
import javax.swing.SwingUtilities
import javax.swing.JTabbedPane


class Main(title: String) : JFrame() {

    val movieManager = MovieManager(loadConfig())

    init {
        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(1200, 800)
        setLocationRelativeTo(null)

        val tabbedPane = JTabbedPane()
        val existingMoviesPanel = ExistingMoviesPanel(movieManager)
        val unknownMoviesPanel = UnknownMoviesPanel(movieManager)

        tabbedPane.addTab("Existing Movies", existingMoviesPanel)
        tabbedPane.addTab("Unknown Movies", unknownMoviesPanel)

        // creates the menu bar
        val menuBar = JMenuBar()
        val menu = JMenu("File")
        menuBar.add(menu)

        // Create the file menu items
        val item = JMenuItem("Scan NAS")
        menu.add(item)
        item.addActionListener {

            runAsync(this, Runnable {
                movieManager.loadData()
                SwingUtilities.invokeLater {
                    existingMoviesPanel.reloadData(movieManager.createdMovieDirs)
                    unknownMoviesPanel.reloadData(movieManager.unknownMovieDirs)
                }
            })
        }
        jMenuBar = menuBar

        add(tabbedPane)
    }
}

private fun createAndShowGUI() {
    val frame = Main("MovieInfoGenerator")
    frame.isVisible = true
}

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowGUI)
}

fun loadConfig(): Config {
    val configYaml = File(Main::class.java.getResource("/config.yaml").file).readText()
    val mapper = ObjectMapper(YAMLFactory())
    return mapper.readValue(configYaml, Config::class.java)
}
