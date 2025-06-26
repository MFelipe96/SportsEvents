package com.mypackage.sportsevents.data.local.mapper

import com.mypackage.sportsevents.data.local.entity.EventEntity
import com.mypackage.sportsevents.data.remote.dto.EventDto
import com.mypackage.sportsevents.domain.model.Event

fun EventDto.toEntity(sportId: String, sportName: String, isFavorite: Boolean): EventEntity {
    return EventEntity(
        id = this.id,
        sportId = sportId,
        sportName = sportName,
        name = this.name,
        timestamp = this.timestamp,
        isFavorite = isFavorite
    )
}

fun EventEntity.toDomain(): Event {
    return Event(
        id = this.id,
        name = this.name,
        timestamp = this.timestamp,
        isFavorite = this.isFavorite
    )
}
