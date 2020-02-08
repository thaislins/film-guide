package com.thaislins.filmguide.modules.details.model.repository

import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSource
import com.thaislins.filmguide.modules.home.model.Film

class DetailsRepository(private val detailsDataSource: DetailsDataSource) : DetailsDataSource {

    override suspend fun getSimilarFilms(movieId: Int): List<Film> {
        return detailsDataSource.getSimilarFilms(movieId)
    }
}