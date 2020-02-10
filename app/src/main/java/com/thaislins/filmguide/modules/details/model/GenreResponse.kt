package com.thaislins.filmguide.modules.details.model

data class GenreResponse(
    val genres: List<Genre>?
) {
    constructor() : this(null)
}