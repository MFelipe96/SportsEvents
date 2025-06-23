package com.mypackage.sportsevents.data.mapper

import com.mypackage.sportsevents.data.EventDto
import com.mypackage.sportsevents.domain.Event

fun EventDto.toDomain(): Event {
    return Event(
        id = id,
        name = name,
        timestamp = timestamp
    )
}