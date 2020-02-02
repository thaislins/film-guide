package com.thaislins.filmguide.modules.film.model.repository

import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.datasource.FilmDataSource

class FilmRepository(private val filmDataSource: FilmDataSource) : FilmDataSource {

    override suspend fun loadAllFilms(): List<Film>? {
        return filmDataSource.loadAllFilms()
    }
}