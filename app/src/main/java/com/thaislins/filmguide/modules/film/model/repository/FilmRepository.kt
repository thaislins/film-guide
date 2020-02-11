package com.thaislins.filmguide.modules.film.model.repository

import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSource
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.HomeDataSourceRemote

class FilmRepository(
    private val remoteDataSource: HomeDataSourceRemote,
    private val localDataSource: HomeDataSourceLocal
) : HomeDataSource {

    var currentPage: Int = 0

    suspend fun searchFilms(text: String): List<Film> {
        return remoteDataSource.searchFilms(text)
    }

    suspend fun loadMoreFilms(movieFilter: Int): List<Film>? {
        return if (currentPage <= remoteDataSource.totalPages[movieFilter]!!) {
            loadFilms(movieFilter, currentPage)
        } else {
            return null
        }
    }

    fun setCurrentPage(movieFilter: Int, filmsSize: Int?) {
        currentPage = (filmsSize?.div(20))?.plus(1)!!
    }

    suspend fun loadDBFilms(movieFilter: Int): List<Film>? {
        val list = localDataSource.loadFilms(movieFilter, currentPage)
        setCurrentPage(movieFilter, list?.size)
        return list
    }

    override suspend fun loadFilms(movieFilter: Int, page: Int): List<Film>? {
        val list = remoteDataSource.loadFilms(movieFilter, page)
        list?.forEach { it.filter = movieFilter }
        list?.forEach { localDataSource.save(it) }
        return list
    }
}