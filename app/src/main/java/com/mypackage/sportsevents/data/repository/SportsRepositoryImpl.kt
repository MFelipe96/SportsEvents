package com.mypackage.sportsevents.data.repository

import android.accounts.NetworkErrorException
import com.mypackage.sportsevents.data.remote.api.SportsApi
import com.mypackage.sportsevents.data.remote.mapper.toDomain
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository
import retrofit2.HttpException
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi
) : SportsRepository {
    override suspend fun getAllSports(): List<Sport> {
        try {
            val response = api.getSports()
            return response.map { it.toDomain() }
        } catch (e: HttpException) {
            throw NetworkErrorException("Server error: ${e.code()}")
        }catch (e: Exception) {
            throw e
        }
    }
}