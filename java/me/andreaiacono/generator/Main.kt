package main.java.me.andreaiacono.generator

import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val template = Template()
    val cover = ImageIO.read(File(Generator::class.java.getResource("/cover.jpg").file))
    val background = ImageIO.read(File(Generator::class.java.getResource("/background.jpg").file))
    val movie = Movie(
        "Arrival",
        "Linguistics professor Louise Banks (Amy Adams) leads an elite team of investigators when gigantic spaceships touch down in 12 locations around the world. As nations teeter on the verge of global war, Banks and her crew must race against time to find a way to communicate with the extraterrestrial visitors. Hoping to unravel the mystery, she takes a chance that could threaten her life and quite possibly all of mankind.",
        listOf(
            "Amy Adams",
            "Jeremy Renner",
            "Forest Whitaker",
            "Michael Stuhlbarg",
            "Tzi Ma"
        ),
        listOf("Denis Villeneuve"),
        116,
        MovieResolution.ULTRA_HD
    )
    val generator = Generator(movie, background, cover, template)

    ImageIO.write(generator.generate(), "PNG", File("result.png"))
}