package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface FilmDataSource {
    suspend fun loadFilms(movieType: Int): List<Film>?
    suspend fun save(film: Film, movieType: Int)
}