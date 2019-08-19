package com.example.topmovies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmpDAO{
    @Query("SELECT * FROM movie_items")
    fun getAll(): List<EmpEntity>

    @Insert
    fun saveAll(vararg movie: EmpEntity)
}