package com.thaislins.filmguide.modules.details.model.repository

import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSource

class DetailsRepository(private val detailsDataSource: DetailsDataSource) : DetailsDataSource {

    override fun getMovieInfo() {
        detailsDataSource.getMovieInfo()
    }
}