package com.thaislins.filmguide.data.remote

import com.thaislins.filmguide.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class FilmApi {

    /**
     * Creates a retrofit object to be used for a request
     *
     */
    fun getFilmService(): FilmService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(createLoggingInterceptor())
            .build()

        return retrofit.create(FilmService::class.java)
    }

    /**
     * Creates logging object that logs details about related request
     *
     */
    private fun createLoggingInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}