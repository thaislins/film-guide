package com.thaislins.filmguide.modules.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.MovieType
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceRemote
import com.thaislins.filmguide.modules.home.model.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FilmViewModel : ViewModel(), KoinComponent {

    private val tmdbApi: TMDBApi? by inject()
    private val repository = FilmRepository(FilmDataSourceRemote(tmdbApi?.getFilmService()!!))
    val trendingFilms = MutableLiveData<List<Film>>().apply { value = null }
    val popularFilms = MutableLiveData<List<Film>>().apply { value = null }
    val nowPlaying = MutableLiveData<List<Film>>().apply { value = null }
    val topRated = MutableLiveData<List<Film>>().apply { value = null }

    fun loadFilms(movieType: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (movieType) {
                    MovieType.TRENDING.ordinal -> trendingFilms.postValue(repository.loadFilms(movieType))
                    MovieType.POPULAR.ordinal -> popularFilms.postValue(repository.loadFilms(movieType))
                    MovieType.NOWPLAYING.ordinal -> nowPlaying.postValue(repository.loadFilms(movieType))
                    else -> topRated.postValue(repository.loadFilms(movieType))
                }
            } catch (ex: Exception) {
            }
        }
    }
}