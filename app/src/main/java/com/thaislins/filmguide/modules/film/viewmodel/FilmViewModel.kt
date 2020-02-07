package com.thaislins.filmguide.modules.film.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.datasource.FilmDataSourceRemote
import com.thaislins.filmguide.modules.film.model.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FilmViewModel : ViewModel(), KoinComponent {

    private val tmdbApi: TMDBApi? by inject()
    private val repository = FilmRepository(FilmDataSourceRemote(tmdbApi?.getFilmService()!!))
    val films = MutableLiveData<List<Film>>().apply { value = null }
    val trendingFilms = MutableLiveData<List<Film>>().apply { value = null }

    fun loadTrending() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                trendingFilms.postValue(repository.loadTrendingFilms())
            } catch (ex: Exception) {
            }
        }
    }

    fun loadFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                films.postValue(repository.loadAllPopularFilms())
            } catch (ex: Exception) {
            }
        }
    }
}