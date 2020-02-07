package com.thaislins.filmguide.modules.film.model.datasource

import com.thaislins.filmguide.modules.film.model.Film

interface FilmDataSource {
    suspend fun loadFilms(movieType: Int): List<Film>?
}