package com.example.topmovies.models

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_average")
    val rating: String,
    @SerializedName("release_date")
    val releaseDate: String
)
