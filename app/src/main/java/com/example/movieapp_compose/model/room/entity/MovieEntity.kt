package com.example.movieapp_compose.model.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
 class MovieEntity(
    @PrimaryKey @SerializedName("imdbID")
    val id: String,

    @ColumnInfo(name = "title") @SerializedName("Title")
    var title : String,

    @ColumnInfo(name = "poster") @SerializedName("Poster")
    var poster : String,

    @ColumnInfo(name = "year") @SerializedName("Year")
    var year : String,

    @ColumnInfo(name = "type") @SerializedName("Type")
    var type : String,

    @ColumnInfo(name = "userId") @SerializedName("UserId")
    val userId: Long
)
