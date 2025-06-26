package com.mypackage.sportsevents.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val sportId: String,
    val sportName: String,
    val name: String,
    val timestamp: Long,
    val isFavorite: Boolean = false
)
