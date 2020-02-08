package com.thaislins.filmguide.data.remote

import com.thaislins.filmguide.modules.details.model.GenreResponse
import com.thaislins.filmguide.modules.home.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: Int
    ): Response?

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarFilms(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
        @Query("language") language: String, @Query("page") page: Int
    ): Response?

    @GET("3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String, @Query("language") language: String
    ): GenreResponse?
}