package me.andreaiacono.generator.util

import net.bramp.ffmpeg.FFprobe

fun getMovieMetadata(ffprobePath: String, filename: String): String {
    val ffprobe = FFprobe(ffprobePath)
    val probeResult = ffprobe.probe(filename)
    val stream = probeResult.getStreams()[0]
    return "${stream.width}x${stream.height} ${(stream.avg_frame_rate.numerator/stream.avg_frame_rate.denominator)}f/s [${stream.codec_name}]"
}
