package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface DetailsDataSource {

    suspend fun getSimilarFilms(movieId: Int): List<Film>
}