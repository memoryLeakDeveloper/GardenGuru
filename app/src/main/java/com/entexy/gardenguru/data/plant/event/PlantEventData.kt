package com.entexy.gardenguru.data.plant.event

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.PlantData
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlantEventData(
    val eventId: String?,
    val plantCustomName: String?,
    val plantDefaultVariety: String,
    val plantVariety: Map<String, String>,
    val event: EventData.EventType,
    val eventTime: Calendar,
    var isComplete: Boolean,
) : Parcelable {

    companion object {
        fun fromPlantsAndEvents(plants: List<PlantData>, events: List<EventData>): ArrayList<PlantEventData> {
            val result = arrayListOf<PlantEventData>()

            events.forEach { event ->
                val plant = plants.find { it.id == event.plantId } ?: return@forEach
                result.add(
                    PlantEventData(
                        event.id,
                        plant.name,
                        plant.variety,
                        plant.localizedVariety,
                        event.eventType,
                        event.eventTime,
                        event.isCompleted
                    )
                )
            }

            return result
        }
    }

}