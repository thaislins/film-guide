package com.thaislins.filmguide.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.thaislins.filmguide.core.AppDatabase
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSourceRemote
import com.thaislins.filmguide.modules.details.model.repository.DetailsRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FilmGuideApplication : Application() {

    val tmdbApi: TMDBApi by inject()

    private val listofModules = module {
        single { TMDBApi() }
        single { DetailsRepository(DetailsDataSourceRemote(tmdbApi.getFilmService())) }

        // Database
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "film_guide_db").build() }
        single { get<AppDatabase>().filmDao() }
    }

    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        startKoin {
            androidContext(this@FilmGuideApplication)
            androidLogger()
            modules(listofModules) // declare modules
        }
    }
}