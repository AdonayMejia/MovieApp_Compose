package com.example.movieapp_compose.ui.favoritesview

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: MovieViewModel
) {
    val context = LocalContext.current
    val id = getUserId(context)

    viewModel.getMovieFav(id.toLong())
    val movies by viewModel.movies.collectAsState()

    val pagerState = rememberPagerState()
    HorizontalPager(pageCount = 10, state = pagerState) {
        {

        }
    }

    @Composable
    fun CardItem(page:Int) {
            Card(onClick = { /*TODO*/ },
                modifier = Modifier.graphicsLayer {

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

            }
        }
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(movies) { movie ->
//            AsyncImage(model = movie.poster, contentDescription = "Dex",
//                modifier = Modifier.fillMaxSize())
////            IconButton(onClick = { viewModel.deleteFavoriteMovie(movie) }) {
////                Icon(
////                    delBtn,
////                    contentDescription = "Delete",
////                    modifier = Modifier
////                        .size(50.dp),
////                    tint = MaterialTheme.colorScheme.inversePrimary
////                )
////
////            }
//        }
//    }
}

private fun getUserId(context: Context) : Int {
    val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("userId", 0)
}