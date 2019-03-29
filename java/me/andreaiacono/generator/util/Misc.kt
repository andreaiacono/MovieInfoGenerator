package me.andreaiacono.generator.util

import me.andreaiacono.generator.gui.util.ErrorForm
import net.bramp.ffmpeg.FFprobe
import java.awt.Component
import java.awt.Cursor
import javax.swing.SwingUtilities

fun getMovieMetadata(ffprobePath: String, filename: String): String {
    val ffprobe = FFprobe(ffprobePath)
    try {
        val probeResult = ffprobe.probe(filename)
        val stream = probeResult.getStreams()[0]
        return "${stream.width}x${stream.height} ${(stream.avg_frame_rate.numerator / stream.avg_frame_rate.denominator)}f/s [${stream.codec_name}]"
    }
    catch (ex:Exception) {
        ErrorForm(ex, "An error occurred while launching ffprobe on [$filename]").isVisible = true
    }
    return "N/A"
}


fun runAsync(component: Component, runnable: Runnable) {
    SwingUtilities.invokeLater {
        component.cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
        try {
            runnable.run()
        } finally {
            component.cursor = Cursor.getDefaultCursor()
        }
    }
}