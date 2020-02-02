package com.thaislins.filmguide.modules.film.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.Response

class FilmDataSourceRemote(private val filmService: FilmService) : FilmDataSource {

    private var page: Int = 1
    private var hasItems = true
    private var response: Response? = null

    override suspend fun loadAllFilms(): List<Film>? {
        return if (hasItems) {
            loadFilms()
        } else {
            return null
        }
    }

    suspend fun loadFilms(): List<Film> {
        return try {
            response = filmService.getFilms(API_KEY, "en-US", page.toString())
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