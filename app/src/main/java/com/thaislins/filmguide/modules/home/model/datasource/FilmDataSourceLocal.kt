package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.modules.home.model.Film

class FilmDataSourceLocal(private val dao: FilmDao) : FilmDataSource {

    override suspend fun loadFilms(movieType: Int): List<Film>? {
        return dao.getFilmsOfType(movieType)
    }

    override suspend fun save(film: Film) {
        dao.insertFilm(film)
    }
}