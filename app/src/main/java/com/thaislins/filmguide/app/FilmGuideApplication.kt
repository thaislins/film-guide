package com.thaislins.filmguide.app

import android.app.Application
import android.content.Context
import com.thaislins.filmguide.data.remote.TMDBApi
import com.thaislins.filmguide.modules.film.model.datasource.FilmDataSourceRemote
import com.thaislins.filmguide.modules.film.model.repository.FilmRepository
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FilmGuideApplication : Application() {

    private val tmdbApi: TMDBApi? by inject()

    private val listofModules = module {
        viewModel { FilmViewModel() }
        single { TMDBApi() }
        single { FilmRepository(FilmDataSourceRemote(tmdbApi?.getFilmService()!!)) }
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