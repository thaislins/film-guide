package com.thaislins.filmguide.modules.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thaislins.filmguide.modules.details.model.repository.DetailsRepository
import com.thaislins.filmguide.modules.film.model.Film
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailsViewModel : ViewModel(), KoinComponent {

    val detailsRepository: DetailsRepository by inject()
    var year = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var title = MutableLiveData<String>()

    fun getMovieInfo(film: Film?) {
        title.value = film?.title
        year.value = film?.year
        description.value = film?.overview
    }
}