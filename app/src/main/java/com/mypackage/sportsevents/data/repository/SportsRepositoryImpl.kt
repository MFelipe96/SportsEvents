package com.mypackage.sportsevents.data.repository

import com.mypackage.sportsevents.data.remote.api.SportsApi
import com.mypackage.sportsevents.data.remote.mapper.toDomain
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi
) : SportsRepository {
    override suspend fun getAllSports(): List<Sport> {
        return api.getSports().map { it.toDomain() }
    }
}