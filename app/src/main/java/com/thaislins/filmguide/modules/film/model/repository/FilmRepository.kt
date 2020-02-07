package com.thaislins.filmguide.modules.film.model.repository

import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.datasource.FilmDataSource

class FilmRepository(private val filmDataSource: FilmDataSource) : FilmDataSource {

    override suspend fun loadAllPopularFilms(): List<Film>? {
        return filmDataSource.loadAllPopularFilms()
    }

    override suspend fun loadTrendingFilms(): List<Film>? {
        return filmDataSource.loadTrendingFilms()
    }
}