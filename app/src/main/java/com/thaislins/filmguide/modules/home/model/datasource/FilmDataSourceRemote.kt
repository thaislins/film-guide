package com.thaislins.filmguide.modules.home.model.datasource

import android.util.Log
import com.thaislins.filmguide.BuildConfig.API_KEY
import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.data.remote.FilmService
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.Response

class FilmDataSourceRemote(private val filmService: FilmService) : FilmDataSource {

    val totalPages = hashMapOf<Int, Int?>()
    private val lang = "en-US"
    private var response: Response? = null

    override suspend fun loadFilms(movieFilter: Int, page: Int): List<Film>? {
        return try {
            response = when (movieFilter) {
                MovieFilter.POPULAR.ordinal -> filmService.getPopularFilms(API_KEY, lang, page)
                MovieFilter.TRENDING.ordinal -> filmService.getTrendingFilms(API_KEY)
                MovieFilter.NOWPLAYING.ordinal -> filmService.getNowPlaying(API_KEY, lang, page)
                else -> filmService.getTopRated(API_KEY, lang, page)
            }

            if (response != null) {
                totalPages[movieFilter] = response?.totalPages
                response?.results!!
            } else {
                throw Exception()
            }
        } catch (ex: Exception) {
            Log.e("EXCEPTION", ex.message.toString())
            throw Exception()
        }
    }
}