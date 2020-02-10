package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.modules.home.model.Film

class FilmDataSourceLocal(private val dao: FilmDao) : FilmDataSource {

    override suspend fun loadFilms(movieFilter: Int): List<Film>? {
        return dao.getFilmsOfType(movieFilter)
    }

    override suspend fun save(film: Film) {
        dao.insertFilm(film)
    }
}