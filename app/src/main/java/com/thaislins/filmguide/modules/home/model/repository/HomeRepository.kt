package com.thaislins.filmguide.modules.home.model.repository

import com.thaislins.filmguide.core.isNetworkConnected
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSource
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceRemote

class HomeRepository(
    private val remoteDataSource: FilmDataSourceRemote,
    private val localDataSource: FilmDataSourceLocal
) : FilmDataSource {

    override suspend fun loadFilms(movieType: Int): List<Film>? {
        return if (isNetworkConnected) {
            val list = remoteDataSource.loadFilms(movieType)
            list?.forEach { it.type = movieType }
            list?.forEach { localDataSource.save(it, movieType) }
            list
        } else {
            localDataSource.loadFilms(movieType)
        }
    }

    override suspend fun save(film: Film, movieType: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}