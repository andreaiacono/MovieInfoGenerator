package me.andreaiacono.generator.gui

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import me.andreaiacono.generator.Generator
import me.andreaiacono.generator.model.Config
import me.andreaiacono.generator.service.MovieManager
import java.awt.EventQueue
import java.io.File
import javax.swing.*
import javax.swing.SwingUtilities


class KotlinSwingSimpleEx(title: String) : JFrame() {

    val movieManager = MovieManager(loadConfig())

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 200)
        setLocationRelativeTo(null)

        val existingPostersModel = DefaultListModel<String>()
        existingPostersModel.addElement("Jane Doe")
        existingPostersModel.addElement("John Smith")
        existingPostersModel.addElement("Kathy Green")

        val notExistingPostersModel = DefaultListModel<String>()
        existingPostersModel.addElement("Jsdqs")
        existingPostersModel.addElement("Johsqdaith")
        existingPostersModel.addElement("Kaqsdsqreen")


        val existingMoviesList = JList(existingPostersModel)
        add(existingMoviesList)

        val notExistingMoviesList = JList(notExistingPostersModel)
        add(notExistingMoviesList)

        // creates the menu bar
        val menuBar = JMenuBar()

        // Create the file menu
        val menu = JMenu("File")
        menuBar.add(menu)

        // Create the file menu items
        val item = JMenuItem("Load")
        menu.add(item)

        item.addActionListener {
            Thread(Runnable {
                movieManager.loadData()
                SwingUtilities.invokeLater {
//                    existingPostersModel.clear()
                    existingPostersModel.addAll(movieManager.createdMovieDirs)

//                    notExistingPostersModel.clear()
                    notExistingPostersModel.addAll(movieManager.createdMovieDirs)
                }
            }).start()
        }
        jMenuBar = menuBar
    }
}

private fun createAndShowGUI() {

    val frame = KotlinSwingSimpleEx("MovieInfoGenerator")
    frame.isVisible = true

}

fun main(args: Array<String>) {

    EventQueue.invokeLater(::createAndShowGUI)
}

fun loadConfig(): Config {
    val configYaml = File(Generator::class.java.getResource("/config.yaml").file).readText()
    val mapper = ObjectMapper(YAMLFactory())
    return mapper.readValue(configYaml, Config::class.java)
}
