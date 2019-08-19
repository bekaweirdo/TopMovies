package com.example.topmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_items")
class EmpEntity {

    @PrimaryKey(autoGenerate = true)
    var empId: Int = 0

    @ColumnInfo(name = "TITLE")
    var empTitle: String = ""
    @ColumnInfo(name = "ORIGINAL_TITLE")
    var empOriginalTitle: String = ""
    @ColumnInfo(name = "OVERVIEW")
    var empOverView: String = ""
    @ColumnInfo(name = "RATING")
    var empRating: String = ""
    @ColumnInfo(name = "RELEASE_DATE")
    var empRelease: String = ""
    @ColumnInfo(name = "POSTER_PATH")
    var empPosterPath: String = ""
}