package com.thaislins.filmguide.modules.home.model.datasource

import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.home.model.Film
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

    override suspend fun loadFilms(movieFilter: Int): List<Film>? {
        return try {
            response = when (movieFilter) {
                MovieFilter.POPULAR.ordinal -> filmService.getPopularFilms(API_KEY, lang, page)
                MovieFilter.TRENDING.ordinal -> filmService.getTrendingFilms(API_KEY)
                MovieFilter.NOWPLAYING.ordinal -> filmService.getNowPlaying(API_KEY, lang, page)
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

    override suspend fun save(film: Film, movieFilter: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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