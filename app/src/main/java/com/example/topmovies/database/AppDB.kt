package com.example.topmovies.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(EmpEntity::class)],version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun empDAO(): EmpDAO
}