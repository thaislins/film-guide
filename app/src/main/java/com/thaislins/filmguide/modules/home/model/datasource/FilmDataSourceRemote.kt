package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.MovieType
import com.thaislins.filmguide.modules.home.model.Response

class FilmDataSourceRemote(private val filmService: FilmService) : FilmDataSource {

    private var page: Int = 1
    private val lang = "en-US"
    //private var hasItems = true
    private var response: Response? = null

    /* override suspend fun loadAllPopularFilms(): List<Film>? {
         return if (hasItems) {
             loadPopularFilms()
         } else {
             return null
         }
     }*/

    override suspend fun loadFilms(movieType: Int): List<Film> {
        return try {
            response = when (movieType) {
                MovieType.POPULAR.ordinal -> filmService.getPopularFilms(API_KEY, lang, page)
                MovieType.TRENDING.ordinal -> filmService.getTrendingFilms(API_KEY)
                MovieType.NOWPLAYING.ordinal -> filmService.getNowPlaying(API_KEY, lang, page)
                else -> filmService.getTopRated(API_KEY, lang, page)
            }

            if (response != null) {
                response?.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }

    /*suspend fun loadPopularFilms(): List<Film> {
        return try {
            response = filmService.getPopularFilms(API_KEY, "en-US", page.toString())
            //page += page
            //hasItems = page <= response?.totalPages!!

            if (response != null) {
                response?.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception()
        }
    }*/
}