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

    override suspend fun loadFilms(movieFilter: Int): List<Film>? {
        return if (isNetworkConnected) {
            val list = remoteDataSource.loadFilms(movieFilter)
            list?.forEach { it.filter = movieFilter }
            list?.forEach { localDataSource.save(it) }
            list
        } else {
            localDataSource.loadFilms(movieFilter)
        }
    }
}