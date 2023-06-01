package com.example.movieapp_compose.model.room.repository

import com.example.movieapp_compose.model.room.dao.MovieDao
import com.example.movieapp_compose.model.room.entity.MovieEntity

class MoviesRepository(private val movieDao: MovieDao) {
    fun addFavMovie(favoriteMovie: MovieEntity) {
        movieDao.insertAllMovies(favoriteMovie)
    }

    fun getFavMovie(userId: Long): List<MovieEntity> {
        return movieDao.getMovieById(userId)
    }

    fun deleteFavMovie(favMovie: MovieEntity) {
        movieDao.delete(favMovie)
    }
}