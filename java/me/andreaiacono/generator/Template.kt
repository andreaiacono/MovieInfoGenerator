package main.java.me.andreaiacono.generator

import java.awt.image.BufferedImage
import javax.imageio.ImageIO.read

class Template {

    val frame: BufferedImage = read(Template::class.java.getResource("/frame_short.png"))

}