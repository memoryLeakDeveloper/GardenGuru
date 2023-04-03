package com.entexy.gardenguru.data.plant.event

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.PlantData
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlantEventData(
    var eventId: String?,
    val plantId: String,
    val plantCustomName: String?,
    val plantDefaultVariety: String,
    val plantVariety: Map<String, String>,
    val event: EventData.EventType,
    val eventTime: Calendar,
    var isComplete: Boolean,
) : Parcelable, Cloneable {

    public override fun clone(): Any {
        return super.clone()
    }

    override fun toString(): String =
        "[id: $eventId; isComplete: $isComplete, eventTime: ${eventTime.time}, eventType: ${event.cloudValue}, plantId: $plantId]"


    fun mapToEventData() =
        EventData(eventId, isComplete, eventTime, event, plantId)

    companion object {
        fun fromPlantsAndEvents(plants: List<PlantData>, events: List<EventData>): ArrayList<PlantEventData> {
            val result = arrayListOf<PlantEventData>()

            events.forEach { event ->
                val plant = plants.find { it.id == event.plantId } ?: return@forEach
                result.add(
                    PlantEventData(
                        event.id,
                        event.plantId,
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