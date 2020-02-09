package com.thaislins.filmguide.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.modules.home.model.Film

@Database(entities = [Film::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
}