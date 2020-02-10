package com.thaislins.filmguide.modules.home.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Response(
    var page: Int,
    @get:JsonProperty("total_results")
    var totalResuls: Int,
    @get:JsonProperty("total_pages")
    var totalPages: Int,
    var results: List<Film>?
) {
    constructor() : this(0, 0, 0, null)
}