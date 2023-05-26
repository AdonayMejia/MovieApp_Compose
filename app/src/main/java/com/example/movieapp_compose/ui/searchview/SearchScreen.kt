package com.example.movieapp_compose.ui.searchview

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.R
import com.example.movieapp_compose.model.api.RetrofitInstance.searchMovies
import com.example.movieapp_compose.model.datamodel.MovieDetails
import kotlinx.coroutines.runBlocking

@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<MovieDetails>>(emptyList()) }

    Column(modifier = Modifier
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
        MovieList(movies = movies)
    }
}

@Composable
fun MovieList(movies: List<MovieDetails>) {
    LazyColumn{
        items(movies){movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(movie: MovieDetails) {
    Row(modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = rememberAsyncImagePainter(
            model = movie.poster),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(90.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = movie.title)
        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MoviesPreview() {
    SearchScreen()
}