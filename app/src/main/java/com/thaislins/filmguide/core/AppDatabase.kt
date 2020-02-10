package com.thaislins.filmguide.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thaislins.filmguide.data.local.FilmDao
import com.thaislins.filmguide.data.local.VideoDao
import com.thaislins.filmguide.modules.details.model.Video
import com.thaislins.filmguide.modules.home.model.Film

@Database(entities = [Film::class, Video::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    abstract fun videoDao(): VideoDao
}