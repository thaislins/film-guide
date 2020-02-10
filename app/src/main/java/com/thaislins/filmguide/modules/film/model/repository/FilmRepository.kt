package com.thaislins.filmguide.modules.film.model.repository

import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSource
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceRemote

class FilmRepository(
    private val remoteDataSource: FilmDataSourceRemote,
    private val localDataSource: FilmDataSourceLocal
) : FilmDataSource {

    var currentPage: Int = 0

    suspend fun loadMoreFilms(movieFilter: Int): List<Film>? {
        return if (currentPage <= remoteDataSource.totalPages[movieFilter]!!) {
            loadFilms(movieFilter, currentPage)
        } else {
            return null
        }
    }

    fun setCurrentPage(movieFilter: Int, filmsSize: Int?) {
        val itemsPerPage =
            remoteDataSource.totalResults[movieFilter]?.div(remoteDataSource.totalPages[movieFilter]!!)!!
        currentPage = (filmsSize?.div(itemsPerPage))?.plus(1)!!
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