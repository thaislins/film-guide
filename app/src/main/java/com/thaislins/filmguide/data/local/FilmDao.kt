package com.thaislins.filmguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thaislins.filmguide.modules.home.model.Film

@Dao
abstract class FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insertFilm(entity: Film)

    @Query("SELECT * FROM film")
    abstract fun getAll(): List<Film>

    @Query("SELECT * FROM film WHERE filter = :movieType")
    abstract fun getFilmsOfType(movieType: Int): List<Film>
}