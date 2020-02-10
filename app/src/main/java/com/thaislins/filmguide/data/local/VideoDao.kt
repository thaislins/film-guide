package com.thaislins.filmguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thaislins.filmguide.modules.details.model.Video

@Dao
abstract class VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insertTrailer(entity: Video)

    @Query("SELECT * FROM video where movieId=:movieId")
    abstract fun getAll(movieId: Int): List<Video>
}