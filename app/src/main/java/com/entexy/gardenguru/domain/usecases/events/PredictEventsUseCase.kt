package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.utils.isSummerSeason
import java.util.*
import javax.inject.Inject

class PredictEventsUseCase @Inject constructor() {

    fun predictEvents(plantData: PlantData, events: ArrayList<EventData>, predictCount: Int): List<EventData> {
        val sortedEvents = events.sortedByDescending { it.eventTime.time }

        val result = mutableListOf<EventData>()

        val lastWatering = sortedEvents.find { it.eventType == EventData.EventType.Watering }
        val lastWateringTime = if (lastWatering?.isCompleted == true) lastWatering.eventTime.time
        else {
            result.add(EventData(null, false, Calendar.getInstance(), EventData.EventType.Watering))
            Date()
        }

        val lastSpraying = sortedEvents.find { it.eventType == EventData.EventType.Spraying }
        val lastSprayingTime = if (lastSpraying?.isCompleted == true) lastSpraying.eventTime.time
        else {
            result.add(EventData(null, false, Calendar.getInstance(), EventData.EventType.Spraying))
            Date()
        }

        val lastFeeding = sortedEvents.find { it.eventType == EventData.EventType.Feeding }
        val lastFeedingTime = if (lastFeeding?.isCompleted == true) lastFeeding.eventTime.time
        else {
            result.add(EventData(null, false, Calendar.getInstance(), EventData.EventType.Feeding))
            Date()
        }

        val lastDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, predictCount)
        }

        val wateringPrediction =
            predictFutureEvents(lastWateringTime, EventData.EventType.Watering, plantData.wateringSummer, plantData.wateringWinter, lastDay)

        val sprayingPrediction =
            predictFutureEvents(lastSprayingTime, EventData.EventType.Spraying, plantData.sprayingSummer, plantData.sprayingWinter, lastDay)

        val feedingPrediction =
            predictFutureEvents(lastFeedingTime, EventData.EventType.Feeding, plantData.feedingSummer, plantData.feedingWinter, lastDay)

        result.addAll(wateringPrediction)
        result.addAll(sprayingPrediction)
        result.addAll(feedingPrediction)
        return result.sortedByDescending { it.eventTime.time }
    }

    private fun predictFutureEvents(
        lastEventTime: Date,
        eventType: EventData.EventType,
        eventPeriodSummer: Int,
        eventPeriodWinter: Int,
        lastPredictionDay: Calendar
    ): List<EventData> {
        val currentDate = Calendar.getInstance().apply {
            time = lastEventTime
        }

        val eventPrediction = mutableListOf<EventData>()

        while (currentDate.timeInMillis < lastPredictionDay.timeInMillis) {
            currentDate.add(
                Calendar.DAY_OF_YEAR, if (currentDate.isSummerSeason()) eventPeriodSummer else eventPeriodWinter
            )
            eventPrediction.add(EventData(null, false, currentDate, eventType))
        }

        return eventPrediction
    }
}