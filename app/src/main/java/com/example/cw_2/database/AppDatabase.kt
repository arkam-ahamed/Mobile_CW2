package com.example.cw_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cw_2.dao.MovieDao
import com.example.cw_2.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}