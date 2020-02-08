package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.modules.home.model.Film

class DetailsDataSourceImp() : DetailsDataSource {

    override fun getMovieDetails(film: Film?) {
        film?.id?.let { getSimilarMovies(it) }
    }

    fun getGenres() {

    }

    fun getVideos() {

    }

    fun getSimilarMovies(movieId: Int) {

    }
}