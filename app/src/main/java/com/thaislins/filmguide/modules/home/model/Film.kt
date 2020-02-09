package com.thaislins.filmguide.modules.home.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

enum class MovieType { TRENDING, POPULAR, TOPRATED, NOWPLAYING }

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Film(
    @PrimaryKey var id: Int,
    var title: String,
    var overview: String,
    @get:JsonProperty("poster_path") var posterPath: String,
    @get:JsonProperty("backdrop_path") var backdropPath: String,
    @get:JsonProperty("release_date") var year: String,
    var video: String,
    @Ignore @get:JsonProperty("genre_ids") var genreIds: List<Int>?,
    var isWatched: Boolean,
    var filter: Int
) : Parcelable {
    constructor() : this(0, "", "", "", "", "", "", null, false, -1)
}