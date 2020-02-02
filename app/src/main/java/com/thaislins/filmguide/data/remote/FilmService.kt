package com.thaislins.filmguide.data.remote

import com.thaislins.filmguide.modules.film.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    //3/movie/popular?api_key=
    //language=en-US&page=1
    @GET("3/movie/popular?")
    suspend fun getFilms(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: String
    ): Response?
}