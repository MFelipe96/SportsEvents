package com.mypackage.sportsevents.data.mapper

import com.mypackage.sportsevents.data.SportDto
import com.mypackage.sportsevents.domain.Sport

fun SportDto.toDomain(): Sport {
    return Sport(
        id = id,
        name = name,
        events = events.map { it.toDomain() }
    )
}