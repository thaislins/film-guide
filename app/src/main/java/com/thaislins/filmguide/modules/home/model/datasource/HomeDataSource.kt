package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface HomeDataSource {
    suspend fun loadFilms(movieFilter: Int, page: Int): List<Film>?
}