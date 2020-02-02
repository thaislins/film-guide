package com.thaislins.filmguide.modules.film.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.film.model.Film
import java.io.IOException

class FilmDataSourceRemote(private val filmService: FilmService) : FilmDataSource {
    override suspend fun loadFilms(): List<Film> {
        return try {
            val response = filmService.getFilms(API_KEY, "en-US", "1")

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