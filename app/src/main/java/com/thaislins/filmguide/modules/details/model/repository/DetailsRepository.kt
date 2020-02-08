package com.thaislins.filmguide.modules.details.model.repository

import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSource
import com.thaislins.filmguide.modules.home.model.Film

class DetailsRepository(private val detailsDataSource: DetailsDataSource) : DetailsDataSource {

    override fun getMovieDetails(film: Film?) {
        detailsDataSource.getMovieDetails(film)
    }
}