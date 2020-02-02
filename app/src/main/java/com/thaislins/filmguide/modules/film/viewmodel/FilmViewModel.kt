package com.thaislins.filmguide.modules.film.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FilmViewModel : ViewModel(), KoinComponent {

    private val repository: FilmRepository by inject()
    val films = MutableLiveData<List<Film>>().apply { value = null }

    fun loadFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                films.postValue(repository.loadFilms())
            } catch (ex: Exception) {
                Log.e("Error", ex.message)
            }
        }
    }
}