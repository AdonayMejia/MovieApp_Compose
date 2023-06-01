package com.example.movieapp_compose.ui.searchview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.model.api.RetrofitInstance.searchMovies
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.ui.searchview.component.BottomSheet
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun SearchScreen(model: MovieViewModel) {

    var searchQuery by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<MovieDetails>>(emptyList()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { textValue ->
                searchQuery = textValue
            },
            label = {
                Text(text = "Search Movies")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { //Call the API when the search button is clicked
                movies = runBlocking { searchMovies(searchQuery) }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MovieList(movies = movies, model = model)
    }
}

@Composable
fun MovieList(movies: List<MovieDetails>, model: MovieViewModel) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie, viewModel = model)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(movie: MovieDetails, viewModel: MovieViewModel) {
    val context = LocalContext.current
    val scaffoldState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isFilled by remember { mutableStateOf(false) }
    BottomSheet(movie = movie, sheetState = scaffoldState, onHideBottomSheet = {
        scope.launch { scaffoldState.hide() }
    }
    )
    Row(modifier = Modifier
        .padding(16.dp)
        .clickable {
            scope.launch {
                scaffoldState.partialExpand()
            }
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = movie.poster
            ),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title)
            IconButton(
                onClick = {
                    viewModel.addMovieToFavorite(movie, viewModel.getUserId(context))
                    isFilled = !isFilled
                },
                content = {
                    if (isFilled) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }

    }
}
