package com.example.movieapp_compose.model.api

import com.example.movieapp_compose.model.datamodel.MovieSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String
    ): Response<MovieSearch>
}