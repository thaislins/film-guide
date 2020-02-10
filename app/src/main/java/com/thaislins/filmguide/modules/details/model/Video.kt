package com.thaislins.filmguide.modules.details.model

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class Video(
    var id: String,
    var key: String,
    var name: String,
    var site: String,
    var type: String
) {
    constructor() : this("", "", "", "", "")
}