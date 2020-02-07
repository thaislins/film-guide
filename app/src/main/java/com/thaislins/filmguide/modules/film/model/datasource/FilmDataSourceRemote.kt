package com.thaislins.filmguide.modules.film.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.Response

class FilmDataSourceRemote(private val filmService: FilmService) : FilmDataSource {

    private var page: Int = 1
    private var hasItems = true
    private var response: Response? = null

    override suspend fun loadAllPopularFilms(): List<Film>? {
        return if (hasItems) {
            loadPopularFilms()
        } else {
            return null
        }
    }

    override suspend fun loadTrendingFilms(): List<Film> {
        return try {
            response = filmService.getTrendingFilms(API_KEY)

            if (response != null) {
                response?.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }

    suspend fun loadPopularFilms(): List<Film> {
        return try {
            response = filmService.getPopularFilms(API_KEY, "en-US", page.toString())
            page += page
            hasItems = page <= response?.totalPages!!

            if (response != null) {
                response?.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}