package me.andreaiacono.generator

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbMovie(
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
    val revenue: Int? = 0,
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
    val voteCount: Int? = 0
)

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
    val logoPath: String? = "",
    @JsonProperty("name")
    val name: String? = "",
    @JsonProperty("origin_country")
    val originCountry: String? = ""
)