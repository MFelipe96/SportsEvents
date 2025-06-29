package com.mypackage.sportsevents.data.remote.mapper

import com.mypackage.sportsevents.data.remote.dto.EventDto
import com.mypackage.sportsevents.domain.model.Event

fun EventDto.toDomain(isFavorite: Boolean): Event {
    return Event(
        id = id,
        name = name,
        timestamp = timestamp,
        isFavorite = isFavorite
    )
}