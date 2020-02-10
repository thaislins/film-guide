package com.thaislins.filmguide.modules.details.model

data class Genre(
    var id: Int,
    var name: String
) {
    constructor() : this(0, "")
}