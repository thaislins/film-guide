package com.thaislins.filmguide.modules.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceRemote
import com.thaislins.filmguide.modules.home.model.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val tmdbApi: TMDBApi? by inject()
    private val filmDao: FilmDao by inject()
    private val repository =
        HomeRepository(
            FilmDataSourceRemote(tmdbApi?.getFilmService()!!),
            FilmDataSourceLocal(filmDao)
        )

    val trendingFilms = MutableLiveData<List<Film>>().apply { value = null }
    val popularFilms = MutableLiveData<List<Film>>().apply { value = null }
    val nowPlaying = MutableLiveData<List<Film>>().apply { value = null }
    val topRated = MutableLiveData<List<Film>>().apply { value = null }
    var filmsLoaded = MutableLiveData<Boolean>().apply { value = false }

    init {
        if (!filmsLoaded.value!!) {
            loadAllFilms()
        } else {
            trendingFilms.value = trendingFilms.value
        }
    }

    fun loadAllFilms() {
        loadFilms(MovieFilter.TRENDING.ordinal)
        loadFilms(MovieFilter.POPULAR.ordinal)
        loadFilms(MovieFilter.NOWPLAYING.ordinal)
        loadFilms(MovieFilter.TOPRATED.ordinal)
    }

    fun loadFilms(movieFilter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (movieFilter) {
                    MovieFilter.TRENDING.ordinal -> trendingFilms.postValue(
                        sortList(
                            movieFilter,
                            repository.loadFilms(movieFilter)
                        )
                    )
                    MovieFilter.POPULAR.ordinal -> popularFilms.postValue(
                        sortList(
                            movieFilter,
                            repository.loadFilms(movieFilter)
                        )
                    )
                    MovieFilter.NOWPLAYING.ordinal -> nowPlaying.postValue(
                        sortList(
                            movieFilter,
                            repository.loadFilms(movieFilter)
                        )
                    )
                    else -> topRated.postValue(
                        sortList(
                            movieFilter,
                            repository.loadFilms(movieFilter)
                        )
                    )
                }
                filmsLoaded.postValue(true)
            } catch (ex: Exception) {
            }
        }
    }

    fun sortList(movieFilter: Int, list: List<Film>?): List<Film>? {
        val sortedList =
            if (movieFilter != MovieFilter.TOPRATED.ordinal) list?.sortedWith(compareByDescending({ it.popularity }))
            else list?.sortedWith(compareByDescending { it.voteAverage * 100 })
        return sortedList
    }
}