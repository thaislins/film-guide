package com.thaislins.filmguide.modules.details.model

data class VideoResponse(
    var id: String,
    var results: List<Video>?
) {
    constructor() : this("", null)
}