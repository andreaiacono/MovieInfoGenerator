package me.andreaiacono.generator.model
import com.fasterxml.jackson.annotation.JsonProperty


data class TmdbSearch(
    @JsonProperty("page")
    val page: Int? = 0,

    @JsonProperty("total_results")
    val totalResults: Int? = 0,

    @JsonProperty("total_pages")
    val totalPages: Int? = 0,

    @JsonProperty("results")
    val results: List<Result?>? = listOf()
) {
    data class Result(
        @JsonProperty("vote_count")
        val voteCount: Int? = 0,

        @JsonProperty("id")
        val id: Int? = 0,

        @JsonProperty("video")
        val video: Boolean? = false,

        @JsonProperty("vote_average")
        val voteAverage: Int? = 0,

        @JsonProperty("title")
        val title: String? = "",

        @JsonProperty("popularity")
        val popularity: Double? = 0.0,

        @JsonProperty("poster_path")
        val posterPath: String? = "",

        @JsonProperty("original_language")
        val originalLanguage: String? = "",

        @JsonProperty("original_title")
        val originalTitle: String? = "",

        @JsonProperty("genre_ids")
        val genreIds: List<Int?>? = listOf(),

        @JsonProperty("backdrop_path")
        val backdropPath: Any? = Any(),

        @JsonProperty("adult")
        val adult: Boolean? = false,

        @JsonProperty("overview")
        val overview: String? = "",

        @JsonProperty("release_date")
        val releaseDate: String? = ""
    )
}