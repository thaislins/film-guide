package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.data.local.VideoDao
import com.thaislins.filmguide.modules.details.model.Genre
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.home.model.Film
import org.koin.core.KoinComponent

class DetailsDataSourceLocal(private val filmDao: FilmDao, private val videoDao: VideoDao) :
    DetailsDataSource, KoinComponent {

    override suspend fun getSimilarFilms(movieId: Int): List<Film> {
        val film = filmDao.getFilm(movieId)
        return filmDao.getFilmsOfType(film.filter)
    }

    override suspend fun getGenres(): List<Genre> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getVideos(movieId: Int): List<Video> {
        return videoDao.getAll(movieId)
    }

    suspend fun saveFilm(film: Film) {
        filmDao.insertFilm(film)
    }

    suspend fun saveVideo(video: Video) {
        videoDao.insertTrailer(video)
    }
}