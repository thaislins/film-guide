package com.thaislins.filmguide.modules.home.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {
    var page: Int = 0
    @JsonProperty("total_results")
    var totalResuls: Int = 0
    @JsonProperty("total_pages")
    var totalPages: Int = 0
    var results: List<Film>? = null
}