package com.thaislins.filmguide.modules.details.model.datasource

import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.modules.details.model.Genre
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.home.model.Film

class DetailsDataSourceLocal(filmDao: FilmDao) : DetailsDataSource {

    override suspend fun getSimilarFilms(movieId: Int): List<Film> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getGenres(): List<Genre> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getVideos(movieId: Int): List<Video>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}