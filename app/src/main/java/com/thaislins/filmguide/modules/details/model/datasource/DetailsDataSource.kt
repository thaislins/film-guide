package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

interface DetailsDataSource {

    fun getMovieDetails(film: Film?)
}