package com.thaislins.filmguide.modules.details.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class Video(
    @PrimaryKey var id: String,
    var key: String,
    var name: String,
    var site: String,
    var type: String,
    var movieId: String
) {
    constructor() : this("", "", "", "", "", "")
}