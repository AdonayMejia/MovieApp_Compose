package com.example.movieapp_compose.model.datamodel

import com.google.gson.annotations.SerializedName

data class MovieDetails (
    @SerializedName("imdbID")
    val id: String,

    @SerializedName("Title")
    var title : String,

    @SerializedName("Poster")
    var poster : String,

    @SerializedName("Year")
    var year : String,

    @SerializedName("Type")
    var type : String,

    @SerializedName("Response")
    var response : String
)