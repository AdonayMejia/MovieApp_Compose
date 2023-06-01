package com.example.movieapp_compose.ui.favoritesview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.model.room.entity.MovieEntity
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    viewModel: MovieViewModel
) {
    val context = LocalContext.current
    val id = viewModel.getUserId(context)

    viewModel.getMovieFav(id)
    val movies by viewModel.movies.collectAsState()

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(pageCount = movies.size, state = pagerState, pageSpacing = 10.dp) { page ->
            Card(
                modifier = Modifier
                    .size(400.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                val movie = movies[page]
                Item(movie = movie)
            }
        }
    }

}

@Composable
fun Item(movie: MovieEntity) {
    Image(
        painter = rememberAsyncImagePainter(movie.poster),
        contentDescription = "Movie Poster",
        modifier = Modifier.fillMaxSize()
    )
}
