package com.example.movieapp_compose.model.api

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.example.movieapp_compose.model.datamodel.MovieDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.omdbapi.com/"

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val omdbApiService: OmdbApiService by lazy {
        retrofit.create(OmdbApiService::class.java)
    }

    suspend fun searchMovies(query: String): List<MovieDetails> {
        val apiKey = "6a337bf1"
        val response = omdbApiService.searchMovies(apiKey, query)
        if (response.isSuccessful){
            val searchResponse = response.body()
            return searchResponse?.search ?: emptyList()
        } else {
            throw Exception("Failed to search movies: ${response.errorBody()}")
        }
    }
}