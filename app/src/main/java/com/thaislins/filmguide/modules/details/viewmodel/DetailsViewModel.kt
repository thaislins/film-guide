package com.thaislins.filmguide.modules.details.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.details.model.repository.DetailsRepository
import com.thaislins.filmguide.modules.home.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailsViewModel : ViewModel(), KoinComponent {

    val detailsRepository: DetailsRepository by inject()
    val trailers = MutableLiveData<List<Video>>().apply { value = null }
    val similarFilms = MutableLiveData<List<Film>>().apply { value = null }
    val isWatched = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()
    var year = MutableLiveData<String>()
    var genres = MutableLiveData<String>()
    var title = MutableLiveData<String>()

    fun getFilmDetails(film: Film?) {
        formatReleaseDate(film?.year)
        title.value = film?.title
        description.value = film?.overview
        isWatched.value = film?.isWatched

        viewModelScope.launch(Dispatchers.IO) {
            try {
                film?.id?.let { similarFilms.postValue(detailsRepository.getSimilarFilms(it)) }
                film?.genreIds?.let { getGenreList(it) }
                film?.id?.let { getTrailers(it) }
            } catch (ex: Exception) {
                Log.e("", ex.toString())
            }
        }
    }

    private suspend fun getGenreList(genreIds: List<Int>) {
        val allGenres = detailsRepository.getGenres()
        val listNames = allGenres.filter { it.id in genreIds }.map { it.name }
        genres.postValue(listNames.joinToString(" | "))
    }

    private suspend fun getTrailers(movieId: Int) {
        val allVideos = detailsRepository.getVideos(movieId)
        val listTrailers = allVideos?.filter { it.name.contains("Trailer") }
        trailers.postValue(listTrailers)
    }

    private fun formatReleaseDate(value: String?) {
        val fromUser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val myFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        try {
            val reformattedStr = myFormat.format(fromUser.parse(value))
            year.value = reformattedStr
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}