package com.example.movieapp_compose.ui.searchview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.model.datamodel.MovieDetails

@Composable
fun MovieDetails(movie: MovieDetails) {

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