package com.example.cw_2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cw_2.model.Movie

@Dao
interface MovieDao {

    @Query("Select * from movie")
    suspend fun getAll(): List<Movie>

    @Query("Select id,title from movie WHERE UPPER(actors) LIKE '%'||:name||'%'")
    suspend fun getAllByActor(name:String?): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: Movie)
}