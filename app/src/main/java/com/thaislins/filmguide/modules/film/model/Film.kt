package com.thaislins.filmguide.modules.film.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Film {
    val id: Int = 0
    val title: String = ""
    val overview: String = ""
    @JsonProperty("poster_path")
    val posterPath: String = ""
    @JsonProperty("backdrop_path")
    val backdropPath: String = ""
    @JsonProperty("release_date")
    val year: String = ""
}