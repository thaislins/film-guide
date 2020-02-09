package com.thaislins.filmguide.modules.details.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Video {
    var id: String = ""
    var key: String = ""
    var name: String = ""
    var site: String = ""
    var type: String = ""
}