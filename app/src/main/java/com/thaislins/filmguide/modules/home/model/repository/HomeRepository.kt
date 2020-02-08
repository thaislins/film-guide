package com.thaislins.filmguide.modules.home.model.repository

import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSource

class HomeRepository(private val filmDataSource: FilmDataSource) : FilmDataSource {

    override suspend fun loadFilms(movieType: Int): List<Film>? {
        return filmDataSource.loadFilms(movieType)
    }
}