package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.modules.details.model.Genre
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.home.model.Film

interface DetailsDataSource {

    suspend fun getSimilarFilms(movieId: Int): List<Film>
    suspend fun getGenres(): List<Genre>
    suspend fun getVideos(movieId: Int): List<Video>?
}