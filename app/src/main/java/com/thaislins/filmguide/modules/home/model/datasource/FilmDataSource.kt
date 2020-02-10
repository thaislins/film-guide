package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface FilmDataSource {
    suspend fun loadFilms(movieFilter: Int): List<Film>?
    suspend fun save(film: Film, movieFilter: Int)
}