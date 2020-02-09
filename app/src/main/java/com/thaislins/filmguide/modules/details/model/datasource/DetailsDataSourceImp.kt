package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.details.model.Genre
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.home.model.Film

class DetailsDataSourceImp(private val filmService: FilmService) : DetailsDataSource {

    override suspend fun getGenres(): List<Genre> {
        return try {
            val response = filmService.getGenreList(API_KEY, "en-US")

            if (response != null) {
                response.genres!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }

    override suspend fun getVideos(movieId: Int): List<Video>? {
        return try {
            val response = filmService.getFilmTrailers(movieId, API_KEY, "en-US")

            if (response != null) {
                response.results
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
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