package com.thaislins.filmguide.modules.details.model.repository

import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.core.isNetworkConnected
import com.thaislins.filmguide.modules.details.model.Genre
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSource
import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSourceLocal
import com.thaislins.filmguide.modules.home.model.Film

class DetailsRepository(
    private val remoteDataSource: DetailsDataSource,
    private val localDataSource: DetailsDataSourceLocal
) : DetailsDataSource {

    override suspend fun getSimilarFilms(movieId: Int): List<Film> {
        return if (isNetworkConnected) {
            val list = remoteDataSource.getSimilarFilms(movieId)
            list.forEach { it.filter = MovieFilter.SIMILAR.ordinal }
            list.forEach { localDataSource.saveFilm(it) }
            list
        } else {
            localDataSource.getSimilarFilms(movieId)
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return remoteDataSource.getGenres()
    }

    override suspend fun getVideos(movieId: Int): List<Video>? {
        return if (isNetworkConnected) {
            val list = remoteDataSource.getVideos(movieId)
            list?.forEach { localDataSource.saveVideo(it) }
            list
        } else {
            localDataSource.getVideos(movieId)
        }
    }
}