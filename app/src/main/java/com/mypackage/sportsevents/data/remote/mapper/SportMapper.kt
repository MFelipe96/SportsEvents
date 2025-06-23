package com.mypackage.sportsevents.data.remote.mapper

import com.mypackage.sportsevents.data.remote.dto.SportDto
import com.mypackage.sportsevents.domain.model.Sport

fun SportDto.toDomain(): Sport {
    return Sport(
        id = id,
        name = name,
        events = events.map { it.toDomain() }
    )
}