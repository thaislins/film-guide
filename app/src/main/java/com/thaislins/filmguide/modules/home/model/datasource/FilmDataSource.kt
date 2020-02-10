package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface FilmDataSource {
    suspend fun loadFilms(movieFilter: Int, page: Int): List<Film>?
}