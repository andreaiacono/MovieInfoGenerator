package me.andreaiacono.generator

import java.awt.image.BufferedImage
import javax.imageio.ImageIO.read

class Template(templateName: String = "default") {

    val frame: BufferedImage = read(Template::class.java.getResource("/template/$templateName/overlay.png"))

}