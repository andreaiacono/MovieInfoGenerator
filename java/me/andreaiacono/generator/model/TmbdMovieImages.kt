package me.andreaiacono.generator.model
import com.fasterxml.jackson.annotation.JsonProperty


data class TmbdMovieImages(
    @JsonProperty("id")
    val id: Int? = 0,
    @JsonProperty("backdrops")
    val backdrops: List<Backdrop?>? = listOf(),
    @JsonProperty("posters")
    val posters: List<Poster?>? = listOf()
) {
    data class Poster(
        @JsonProperty("aspect_ratio")
        val aspectRatio: Double? = 0.0,
        @JsonProperty("file_path")
        val filePath: String? = "",
        @JsonProperty("height")
        val height: Int? = 0,
        @JsonProperty("iso_639_1")
        val iso6391: String? = "",
        @JsonProperty("vote_average")
        val voteAverage: Int? = 0,
        @JsonProperty("vote_count")
        val voteCount: Int? = 0,
        @JsonProperty("width")
        val width: Int? = 0
    )

    data class Backdrop(
        @JsonProperty("aspect_ratio")
        val aspectRatio: Double? = 0.0,
        @JsonProperty("file_path")
        val filePath: String? = "",
        @JsonProperty("height")
        val height: Int? = 0,
        @JsonProperty("iso_639_1")
        val iso6391: String? = "",
        @JsonProperty("vote_average")
        val voteAverage: Double? = 0.0,
        @JsonProperty("vote_count")
        val voteCount: Int? = 0,
        @JsonProperty("width")
        val width: Int? = 0
    )
}