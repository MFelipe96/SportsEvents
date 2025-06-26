package com.mypackage.sportsevents.domain.usecase

import com.mypackage.sportsevents.domain.model.Event
import javax.inject.Inject

class FilterFavoriteEventsUseCase @Inject constructor() {
    operator fun invoke(events: List<Event>): List<Event> {
        return events.filter { it.isFavorite }
    }
}