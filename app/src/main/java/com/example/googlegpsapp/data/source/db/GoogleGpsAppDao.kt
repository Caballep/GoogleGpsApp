package com.example.googlegpsapp.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googlegpsapp.data.source.db.entity.LocationEntity

@Dao
interface GoogleGpsAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity)

    @Query("DELETE FROM locationentity WHERE id = :locationId")
    suspend fun deleteLocation(locationId: Int)

    @Query("SELECT * FROM locationentity")
    suspend fun getLocationEntities(): List<LocationEntity>

}
