package me.andreaiacono.generator.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.util.*


data class TmdbMovieInfo(

    @JsonProperty("adult")
    val adult: Boolean? = false,

    @JsonProperty("backdrop_path")
    val backdropPath: String? = "",

    @JsonProperty("belongs_to_collection")
    val belongsToCollection: Any? = Any(),

    @JsonProperty("budget")
    val budget: Int? = 0,

    @JsonProperty("genres")
    val genres: List<Genre?>? = listOf(),

    @JsonProperty("homepage")
    val homepage: String? = "",

    @JsonProperty("id")
    val id: Int? = 0,

    @JsonProperty("imdb_id")
    val imdbId: String? = "",

    @JsonProperty("original_language")
    val originalLanguage: String? = "",

    @JsonProperty("original_title")
    val originalTitle: String? = "",

    @JsonProperty("overview")
    val overview: String? = "",

    @JsonProperty("popularity")
    val popularity: Double? = 0.0,

    @JsonProperty("poster_path")
    val posterPath: String? = "",

    @JsonProperty("production_companies")
    val productionCompanies: List<ProductionCompany?>? = listOf(),

    @JsonProperty("production_countries")
    val productionCountries: List<ProductionCountry?>? = listOf(),

    @JsonProperty("release_date")
    val releaseDate: String? = "",

    @JsonProperty("revenue")
    val revenue: Long? = 0,

    @JsonProperty("runtime")
    val runtime: Int? = 0,

    @JsonProperty("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = listOf(),

    @JsonProperty("status")
    val status: String? = "",

    @JsonProperty("tagline")
    val tagline: String? = "",

    @JsonProperty("title")
    val title: String? = "",

    @JsonProperty("video")
    val video: Boolean? = false,

    @JsonProperty("vote_average")
    val voteAverage: Double? = 0.0,

    @JsonProperty("vote_count")
    val voteCount: Int? = 0,

    @JsonProperty("credits")
    val credits: Credits? = Credits()
) {
    data class SpokenLanguage(
        @JsonProperty("iso_639_1")
        val iso6391: String? = "",

        @JsonProperty("name")
        val name: String? = ""
    )

    data class Genre(
        @JsonProperty("id")
        val id: Int? = 0,

        @JsonProperty("name")
        val name: String? = ""
    )

    data class ProductionCountry(
        @JsonProperty("iso_3166_1")
        val iso31661: String? = "",

        @JsonProperty("name")
        val name: String? = ""
    )

    data class ProductionCompany(
        @JsonProperty("id")
        val id: Int? = 0,

        @JsonProperty("logo_path")
        val logoPath: Any? = Any(),

        @JsonProperty("name")
        val name: String? = "",

        @JsonProperty("origin_country")
        val originCountry: String? = ""
    )

    data class Credits(
        @JsonProperty("cast")
        val cast: List<Cast?>? = listOf(),

        @JsonProperty("crew")
        val crew: List<Crew?>? = listOf()
    ) {
        data class Cast(
            @JsonProperty("cast_id")
            val castId: Int? = 0,

            @JsonProperty("character")
            val character: String? = "",

            @JsonProperty("credit_id")
            val creditId: String? = "",

            @JsonProperty("gender")
            val gender: Int? = 0,

            @JsonProperty("id")
            val id: Int? = 0,

            @JsonProperty("name")
            val name: String? = "",

            @JsonProperty("order")
            val order: Int? = 0,

            @JsonProperty("profile_path")
            val profilePath: Any? = Any()
        )

        data class Crew(
            @JsonProperty("credit_id")
            val creditId: String? = "",

            @JsonProperty("department")
            val department: String? = "",

            @JsonProperty("gender")
            val gender: Int? = 0,

            @JsonProperty("id")
            val id: Int? = 0,

            @JsonProperty("job")
            val job: String? = "",

            @JsonProperty("name")
            val name: String? = "",

            @JsonProperty("profile_path")
            val profilePath: Any? = Any()
        )
    }

    fun getActors(take: Int): List<String?> {
        return credits!!.cast!!.take(take).map { it!!.name }.toList()
    }

    fun getDirectors(): List<String?> {
        return credits!!.crew!!.filter { it!!.job == "Director" }.map { it!!.name }.toList()
    }

    fun toXml() = """<?xml version="1.0" encoding="UTF-8"?>
<details>
    <movie xmlns:xsi="http:--www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http:--www.w3.org/2001/XMLSchema">
        <title>$title</title>
        <sorting_title>$title</sorting_title>
        <year>${releaseDate?.take(4)}</year>
        <plot>$overview</plot>
        <date>${getCurrentDate()}</date>
        <runtime>$runtime</runtime>
        <seen>false</seen>
        <genres>
${genres?.joinToString("") { "            <genre>${it?.name}</genre>\n" }}        </genres>
        <director>
${getDirectors().joinToString("") { "            <name>$it</name>\n" }}        </director>
        <cast>
${getActors(5).joinToString("") { "            <actor>$it</actor>\n" }}        </cast>
    </movie>
</details>"""

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(Date())
    }
}