package com.space.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.space.database.dao.FavouriteMovieDao
import com.space.database.entity.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}