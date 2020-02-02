package com.thaislins.filmguide.modules.film.model

import com.fasterxml.jackson.annotation.JsonProperty

class Response {
    var page: Int = 0
    @JsonProperty("total_results")
    var totalResuls: Int = 0
    @JsonProperty("total_pages")
    var totalPages: Int = 0
    var results: List<Film>? = null
}