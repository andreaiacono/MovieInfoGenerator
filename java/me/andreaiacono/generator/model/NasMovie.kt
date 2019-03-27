package me.andreaiacono.generator.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.*

private val kotlinXmlMapper = XmlMapper()
    .registerKotlinModule()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)


@JacksonXmlRootElement(localName = "details")
class Details (

    @JacksonXmlProperty(localName = "movie")
    val movie: NasMovie
)


@JacksonXmlRootElement(localName = "movie")
data class NasMovie (

    @JacksonXmlProperty(localName = "title")
    val title: String,

    @JacksonXmlProperty(localName = "sorting_title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val sortingTitle: String?,

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "dd/MM/yyyy"
    )
    @JacksonXmlProperty(localName = "date")
    val date: Date = Date(0L),

    @JacksonXmlElementWrapper(localName = "genres")
    val genres: List<String> = listOf(),

    @JacksonXmlElementWrapper(localName = "cast")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val cast: List<String> = listOf(),

    @JacksonXmlProperty(localName = "director")
    val directors: List<String> = listOf(),

    @JacksonXmlProperty(localName = "year")
    val year: Int,

    val dirName: String = "",
    val videoFilename: String = ""
)

fun fromXml(xml: String): NasMovie = kotlinXmlMapper.readValue(xml, Details::class.java).movie