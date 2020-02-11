package com.thaislins.filmguide.modules.home.model.repository

import com.thaislins.filmguide.core.isNetworkConnected
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSource
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSourceRemote

class HomeRepository(
    val remoteDataSource: HomeDataSourceRemote,
    val localDataSource: HomeDataSourceLocal
) : HomeDataSource {

    override suspend fun loadFilms(movieFilter: Int, page: Int): List<Film>? {
        try {
            return if (isNetworkConnected) {
                val list = remoteDataSource.loadFilms(movieFilter, page)
                list?.forEach { it.filter = movieFilter }
                list?.forEach { localDataSource.save(it) }
                list
            } else {
                localDataSource.loadFilms(movieFilter, page)
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}