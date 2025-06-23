package com.mypackage.sportsevents.domain.model

data class Event(
    val id: String,
    val name: String,
    val timestamp: Long,
    val isFavorite: Boolean = false
)