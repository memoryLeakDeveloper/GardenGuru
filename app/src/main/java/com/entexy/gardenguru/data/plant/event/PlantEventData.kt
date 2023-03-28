package com.entexy.gardenguru.data.plant.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantEventData(
    val eventId: String,
    val plantName: String,
    val plantVariety: String,
    val event: EventData.EventType,
    val dateDMY: String,
    var isComplete: Boolean,
    val executor: String = "",
    val timeOfCompletionHHMM: String = ""
) : Parcelable {

}