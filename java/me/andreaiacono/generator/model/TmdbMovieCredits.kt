package me.andreaiacono.generator.model
import com.fasterxml.jackson.annotation.JsonProperty


data class TmdbMovieCredits(
    @JsonProperty("id")
    val id: Int? = 0,

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
        val profilePath: String? = ""
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