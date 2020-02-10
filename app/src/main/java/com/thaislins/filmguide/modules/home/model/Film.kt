package com.thaislins.filmguide.modules.home.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(primaryKeys = ["id", "filter"])
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Film(
    var id: Int,
    var title: String,
    var overview: String,
    @get:JsonProperty("poster_path") var posterPath: String?,
    @get:JsonProperty("backdrop_path") var backdropPath: String?,
    @get:JsonProperty("release_date") var year: String,
    var video: String,
    @Ignore @get:JsonProperty("genre_ids") var genreIds: List<Int>?,
    var popularity: Int,
    @get:JsonProperty("vote_average") var voteAverage: Float,
    var isWatched: Boolean,
    var filter: Int
) : Parcelable {
    constructor() : this(0, "", "", "", "", "", "", null, 0, 0f, false, 0)
}