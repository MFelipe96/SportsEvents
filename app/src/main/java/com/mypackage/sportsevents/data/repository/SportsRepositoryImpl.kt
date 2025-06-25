package com.mypackage.sportsevents.data.repository

import com.mypackage.sportsevents.data.local.dao.SportsDao
import com.mypackage.sportsevents.data.local.mapper.toDomain
import com.mypackage.sportsevents.data.local.mapper.toEntity
import com.mypackage.sportsevents.data.remote.api.SportsApi
import com.mypackage.sportsevents.data.remote.mapper.toDomain
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi,
    private val sportsDao: SportsDao
) : SportsRepository {
    override suspend fun getAllSports(): List<Sport> {
        return try {
            val apiResponse = api.getSports()

            val entities = apiResponse.flatMap { sportDto ->
                sportDto.events.map { it.toEntity(sportDto.id, sportDto.name) }
            }

            //For now, we will save every time to keep the local storage up to date.
            //If any impact on performance/battery is observed, we can evaluate again
            sportsDao.insertEvents(entities)

            apiResponse.map { sportDto ->
                Sport(
                    id = sportDto.id,
                    name = sportDto.name,
                    events = sportDto.events.map { it.toDomain() }
                )
            }
        } catch (e: Exception) {
            val cachedEvents = sportsDao.getAllEvents()
            if (cachedEvents.isNotEmpty()) {
                cachedEvents.groupBy { it.sportId }.map { (sportId, events) ->
                    Sport(
                        id = sportId,
                        name = events.first().sportName,
                        events = events.map { it.toDomain() }
                    )
                }
            } else {
                throw e
            }
        }
    }

    override suspend fun updateFavoriteStatus(eventId: String, isFavorite: Boolean) {
        sportsDao.updateFavorite(eventId, isFavorite)
    }
}