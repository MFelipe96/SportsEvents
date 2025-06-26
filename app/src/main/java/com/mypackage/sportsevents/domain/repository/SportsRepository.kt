package com.mypackage.sportsevents.domain.repository

import com.mypackage.sportsevents.domain.model.Sport

interface SportsRepository {
    suspend fun getAllSports(): List<Sport>
    suspend fun updateFavoriteStatus(eventId: String, isFavorite: Boolean)
}