package com.mypackage.sportsevents.domain
data class Sport(
    val id: String,
    val name: String,
    val events: List<Event>
)