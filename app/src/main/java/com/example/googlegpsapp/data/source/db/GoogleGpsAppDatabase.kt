package com.example.googlegpsapp.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googlegpsapp.data.source.db.entity.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class GoogleGpsAppDatabase : RoomDatabase() {
    abstract fun googleGpsAppDao(): GoogleGpsAppDao
}
