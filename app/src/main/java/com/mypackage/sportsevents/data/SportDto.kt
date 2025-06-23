package com.mypackage.sportsevents.data

import com.google.gson.annotations.SerializedName

data class SportDto(
    @SerializedName("i") val id: String,
    @SerializedName("d") val name: String,
    @SerializedName("e") val events: List<EventDto>
)