package com.example.topmovies.models

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("results")
    val results: ArrayList<Movie>
)