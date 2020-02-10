package com.thaislins.filmguide.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.thaislins.filmguide.core.AppDatabase
import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.data.local.VideoDao
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSourceLocal
import com.thaislins.filmguide.modules.details.model.datasource.DetailsDataSourceRemote
import com.thaislins.filmguide.modules.details.model.repository.DetailsRepository
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceLocal
import com.thaislins.filmguide.modules.home.model.datasource.FilmDataSourceRemote
import com.thaislins.filmguide.modules.home.model.repository.HomeRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FilmGuideApplication : Application() {

    private val filmDao: FilmDao by inject()
    private val videoDao: VideoDao by inject()
    private val tmdbApi: TMDBApi? by inject()

    private val listofModules = module {
        single { TMDBApi() }
        single {
            DetailsRepository(
                DetailsDataSourceRemote(tmdbApi?.getFilmService()),
                DetailsDataSourceLocal(filmDao, videoDao)
            )
        }
        single {
            HomeRepository(
                FilmDataSourceRemote(tmdbApi?.getFilmService()!!),
                FilmDataSourceLocal(filmDao)
            )
        }

        // Database
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "film_guide_db").build() }
        single { get<AppDatabase>().filmDao() }
        single { get<AppDatabase>().videoDao() }
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