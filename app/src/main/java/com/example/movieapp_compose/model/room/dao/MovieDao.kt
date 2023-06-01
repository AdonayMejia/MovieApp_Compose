package com.example.movieapp_compose.model.room.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp_compose.model.room.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE userId LIKE :userId")
    fun getMovieById(userId: Long): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg favoritesMovies: MovieEntity)

    @Delete
    fun delete(favoriteMovie: MovieEntity)
}