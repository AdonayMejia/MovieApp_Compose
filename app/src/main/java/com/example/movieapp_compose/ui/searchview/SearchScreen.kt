package com.example.movieapp_compose.ui.searchview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.R
import com.example.movieapp_compose.model.api.RetrofitInstance.searchMovies
import com.example.movieapp_compose.model.datamodel.MovieDetails
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(movie: MovieDetails) {
    val scaffoldState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    
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
        Image(painter = rememberAsyncImagePainter(
            model = movie.poster),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = movie.title)
        Column {
            Icon(modifier = Modifier.align(Alignment.End),imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    movie: MovieDetails,
    sheetState: SheetState,
    onHideBottomSheet: () -> Unit
    ) {
  when{
      sheetState.isVisible -> {
          ModalBottomSheet(
              modifier = Modifier.fillMaxWidth(),
              onDismissRequest = onHideBottomSheet,
              sheetState = sheetState
          ){
            MovieDetails(movie = movie)
          }
      }
  }
}


@Composable
fun MovieDetails(movie: MovieDetails ) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = rememberAsyncImagePainter(model = movie.poster),
            contentDescription = "Movie")
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = movie.title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = movie.type,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = movie.year,
            fontWeight = FontWeight.Bold)
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MoviesPreview() {
    SearchScreen()
}