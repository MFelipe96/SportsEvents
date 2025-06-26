package com.mypackage.sportsevents.data.remote.api

import com.mypackage.sportsevents.data.remote.dto.SportDto
import retrofit2.http.GET

interface SportsApi {
    @GET("MockSports/sports.json")
    suspend fun getSports(): List<SportDto>
}