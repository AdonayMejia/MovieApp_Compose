package com.example.movieapp_compose.ui.searchview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.model.room.entity.MovieEntity
import com.example.movieapp_compose.model.room.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val listFavMovie = MutableStateFlow<List<MovieEntity>>(listOf())

    val movies: StateFlow<List<MovieEntity>> get() = listFavMovie

    fun addMovieToFavorite(movie: MovieDetails, userId: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            val favMovie = MovieEntity(
                movie.id,
                movie.title,
                movie.poster,
                movie.year,
                movie.type,
                userId
            )
            repository.addFavMovie(favMovie)

        }
    }

    fun getMovieFav(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favCharacters = repository.getFavMovie(userId)
            listFavMovie.emit(favCharacters)
        }
    }

    fun getUserId(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0).toLong()
    }

    fun deleteUserId(context: Context) {
        val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
        sharedPreferences.edit().putInt("userId", 0).apply()
    }
}
