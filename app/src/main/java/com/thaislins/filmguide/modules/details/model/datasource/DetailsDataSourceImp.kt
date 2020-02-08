package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.home.model.Film

class DetailsDataSourceImp(private val filmService: FilmService) : DetailsDataSource {

    fun getGenres() {

    }

    fun getVideos() {

    }


    override suspend fun getSimilarFilms(movieId: Int): List<Film> {
        return try {
            val response = filmService.getSimilarFilms(movieId, API_KEY, "en-US", 1)

            if (response != null) {
                response.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}