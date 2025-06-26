package com.mypackage.sportsevents

import com.mypackage.sportsevents.domain.model.Event
import com.mypackage.sportsevents.domain.usecase.FilterFavoriteEventsUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterFavoriteEventsUseCaseTest {

    private val filterFavorites = FilterFavoriteEventsUseCase()

    @Test
    fun filterReturnsOnlyFavoriteEvents() {
        val events = listOf(
            Event(id = "1", name = "A vs B", timestamp = 1000, isFavorite = true),
            Event(id = "2", name = "C vs D", timestamp = 2000, isFavorite = false),
            Event(id = "3", name = "E vs F", timestamp = 3000, isFavorite = true)
        )

        val result = filterFavorites(events)

        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("3", result[1].id)
    }
}
