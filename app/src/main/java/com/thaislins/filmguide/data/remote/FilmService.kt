package com.thaislins.filmguide.data.remote

import com.thaislins.filmguide.modules.home.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("3/trending/movie/day")
    suspend fun getTrendingFilms(
        @Query("api_key") apiKey: String
    ): Response?

    @GET("3/movie/popular?")
    suspend fun getPopularFilms(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: Int
    ): Response?

    @GET("3/movie/now_playing/")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: Int
    ): Response?

    //3/movie/top_rated
    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: Int
    ): Response?
}