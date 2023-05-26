package com.example.movieapp_compose.ui.favoritesview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize(),
        ){
        Text(text = "Favorites Screen",
            modifier = Modifier.fillMaxWidth(),
            )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    FavoriteScreen()
}