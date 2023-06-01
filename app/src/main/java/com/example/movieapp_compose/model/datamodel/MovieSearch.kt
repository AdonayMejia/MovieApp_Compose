package com.example.movieapp_compose.model.datamodel

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    @SerializedName("Search")
    val search: List<MovieDetails>,
    val totalResult: Int
)
