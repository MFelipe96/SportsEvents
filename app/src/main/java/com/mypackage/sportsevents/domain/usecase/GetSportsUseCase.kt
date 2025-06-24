package com.mypackage.sportsevents.domain.usecase

import com.mypackage.sportsevents.domain.model.Event
import com.mypackage.sportsevents.domain.model.Sport
import com.mypackage.sportsevents.domain.repository.SportsRepository
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend operator fun invoke(): List<Sport> {
        val sports = repository.getAllSports()
        val now = System.currentTimeMillis() / 1000
        return sports.map { sport ->
            val sortedEvents = sport.events.sortedWith(compareBy<Event> {
                it.timestamp < now
            }.thenBy {
                it.timestamp
            })
            sport.copy(events = sortedEvents)
        }
    }
}