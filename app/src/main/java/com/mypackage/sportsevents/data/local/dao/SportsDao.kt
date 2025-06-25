package com.mypackage.sportsevents.data.local.dao

import androidx.room.*
import com.mypackage.sportsevents.data.local.entity.EventEntity

@Dao
interface SportsDao {

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun clearEvents()

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE id = :eventId")
    suspend fun updateFavorite(eventId: String, isFavorite: Boolean)
}
