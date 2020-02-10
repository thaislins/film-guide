package com.thaislins.filmguide.modules.film.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.modules.film.model.repository.FilmRepository
import com.thaislins.filmguide.modules.home.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class FilmViewModel() : ViewModel(), KoinComponent {

    private val repository: FilmRepository by inject()
    val films = MutableLiveData<List<Film>>().apply { value = null }
    val filmType = MutableLiveData<Int>()
    val loading = MutableLiveData<Boolean>()

    fun loadDBFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            films.postValue(sortList(filmType.value!!, repository.loadDBFilms(filmType.value!!)))
        }
    }

    fun loadMoreFilms() = runBlocking {
        val job = viewModelScope.launch(Dispatchers.IO) {
            films.postValue(repository.loadMoreFilms(filmType.value!!))
        }

        job.join()
        incrementCurrentPage()
        loading.postValue(false)
    }

    fun incrementCurrentPage() {
        repository.currentPage += 1
    }

    fun sortList(movieFilter: Int, list: List<Film>?): List<Film>? {
        val sortedList =
            if (movieFilter != MovieFilter.TOPRATED.ordinal) list?.sortedWith(compareByDescending({ it.popularity }))
            else list?.sortedWith(compareByDescending { it.voteAverage * 100 })
        return sortedList
    }
}