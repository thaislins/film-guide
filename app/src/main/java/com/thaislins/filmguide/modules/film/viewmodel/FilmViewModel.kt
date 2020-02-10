package com.thaislins.filmguide.modules.film.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thaislins.filmguide.modules.home.model.Film

class FilmViewModel() : ViewModel() {

    val films = MutableLiveData<List<Film>>().apply { value = null }
}