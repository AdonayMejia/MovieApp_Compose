package com.example.movieapp_compose.ui.searchview.viewmodel

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
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

    private val favBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.FavoriteBorder)
    private val delBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.DeleteForever)
    private val listFavMovie = MutableStateFlow<List<MovieEntity>>(listOf())

    val favBtn: MutableStateFlow<ImageVector> get() = favBtnStateFlow
    val delBtn: MutableStateFlow<ImageVector> get() = delBtnStateFlow
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

    fun deleteFavoriteMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavMovie(movie)
            getMovieFav(movie.userId)
        }
    }
    fun getMovieFav(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favcharacters = repository.getFavMovie(userId)
            listFavMovie.emit(favcharacters)
        }
    }

     fun deleteUserId(context: Context) {
        val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
        sharedPreferences.edit().putInt("userId", 0).apply()
    }
}