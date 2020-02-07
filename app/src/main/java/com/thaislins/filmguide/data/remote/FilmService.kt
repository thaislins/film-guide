package com.thaislins.filmguide.data.remote

import com.thaislins.filmguide.modules.film.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("3/movie/popular?")
    suspend fun getPopularFilms(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: String
    ): Response?

    @GET("3/trending/movie/day")
    suspend fun getTrendingFilms(
        @Query("api_key") apiKey: String
    ): Response?
}