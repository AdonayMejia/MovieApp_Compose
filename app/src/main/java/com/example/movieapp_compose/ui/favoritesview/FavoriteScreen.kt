package com.example.movieapp_compose.ui.favoritesview

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel

@Composable
fun FavoriteScreen(
    viewModel: MovieViewModel
) {
    val delBtn by viewModel.delBtn.collectAsState()
    val context = LocalContext.current
    val id = getUserId(context)

    viewModel.getMovieFav(id.toLong())
    val movies by viewModel.movies.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            Text("ESTA PELI TE GUTS: ${movie.title}")
            IconButton(onClick = { viewModel.deleteFavoriteMovie(movie) }) {
                Icon(
                    delBtn,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(50.dp),
                    tint = MaterialTheme.colorScheme.inversePrimary
                )

            }
        }
    }
}

private fun getUserId(context: Context) : Int {
    val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("userId", 0)
}