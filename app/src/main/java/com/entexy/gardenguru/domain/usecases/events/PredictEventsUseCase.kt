package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.utils.isDaysEquals
import com.entexy.gardenguru.utils.isSummerSeason
import java.lang.Integer.min
import java.util.*
import javax.inject.Inject

class PredictEventsUseCase @Inject constructor() {

    fun predictTimetableEvents(plants: List<PlantData>, events: List<EventData>): List<EventData> {
        val result = arrayListOf<EventData>()

        plants.forEach { plantData ->
            val pastEvents = predictPastEvents(plantData, events.filter { it.plantId == plantData.id })
            val futureEvents = predictFutureEvents(plantData, events.filter { it.plantId == plantData.id }, 30)
            result.addAll(pastEvents)
            result.addAll(futureEvents)
        }
        return result
    }

    fun predictPastEvents(plantData: PlantData, events: List<EventData>): List<EventData> {
        val sortedEvents = ArrayList(events.sortedByDescending { it.eventTime.time })

        val wateringPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Watering,
            sortedEvents.filter { it.eventType == EventData.EventType.Watering },
            plantData.wateringSummer,
            plantData.wateringWinter,
            plantData.id
        )

        val sprayingPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Spraying,
            sortedEvents.filter { it.eventType == EventData.EventType.Spraying },
            plantData.sprayingSummer,
            plantData.sprayingWinter,
            plantData.id
        )

        val feedingPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Feeding,
            sortedEvents.filter { it.eventType == EventData.EventType.Feeding },
            plantData.feedingSummer,
            plantData.feedingWinter,
            plantData.id
        )

        val filterValue = listOf(
            EventData.EventType.Watering.ordinal,
            EventData.EventType.Spraying.ordinal,
            EventData.EventType.Feeding.ordinal,
        )
        val result = sortedEvents.filter {
            it.eventType.ordinal !in filterValue
        }.toMutableList()
        result.addAll(wateringPrediction)
        result.addAll(sprayingPrediction)
        result.addAll(feedingPrediction)
        return result.sortedByDescending { it.eventTime.time }.filter { it.eventTime.timeInMillis < System.currentTimeMillis() }
    }

    private fun predictPastEvents(
        addingPlantDate: Date,
        eventType: EventData.EventType,
        events: List<EventData>,
        eventPeriodSummer: Int,
        eventPeriodWinter: Int,
        plantId: String
    ): List<EventData> {
        val eventPrediction = mutableListOf<EventData>()
        eventPrediction.addAll(events)

        val currentEventDate = Calendar.getInstance().apply { timeInMillis = addingPlantDate.time }

        val firstPrediction = Calendar.getInstance().apply {
            timeInMillis = currentEventDate.timeInMillis
            add(Calendar.DAY_OF_YEAR, if (currentEventDate.isSummerSeason()) eventPeriodSummer else eventPeriodWinter)
        }
        if (eventPrediction.firstOrNull()?.eventTime?.isDaysEquals(firstPrediction) != true) {
            eventPrediction.add(
                0,
                EventData(
                    null,
                    false,
                    Calendar.getInstance().apply { timeInMillis = firstPrediction.timeInMillis },
                    eventType,
                    plantId
                )
            )
        }
        currentEventDate.timeInMillis = firstPrediction.timeInMillis

        var i = 0

        val currentTime = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis
        while (currentEventDate.timeInMillis <= currentTime) {
            val iEvent = eventPrediction.getOrNull(i)
            val daysAfterEvent = if (iEvent?.isCompleted == true) {
                if (currentEventDate.isSummerSeason()) eventPeriodSummer else eventPeriodWinter
            } else 1

            val predictedTime = Calendar.getInstance().apply {
                timeInMillis = currentEventDate.timeInMillis
                add(Calendar.DAY_OF_YEAR, daysAfterEvent)
            }

            val iNextEvent = eventPrediction.find { it.eventTime.isDaysEquals(predictedTime) }
            if ((predictedTime.timeInMillis < currentTime) && (iNextEvent == null)) {
                eventPrediction.add(
                    min(i + 1, eventPrediction.size - 1),
                    EventData(
                        null,
                        false,
                        Calendar.getInstance().apply { timeInMillis = predictedTime.timeInMillis },
                        eventType,
                        plantId
                    )
                )
            }
            i++
            currentEventDate.timeInMillis = predictedTime.timeInMillis
        }
        return eventPrediction
    }

    fun predictFutureEvents(plantData: PlantData, events: List<EventData>, predictCount: Int): List<EventData> {
        val sortedEvents = events.sortedByDescending { it.eventTime.time }


        val lastWatering = sortedEvents.find { it.eventType == EventData.EventType.Watering }

        val lastSpraying = sortedEvents.find { it.eventType == EventData.EventType.Spraying }

        val lastFeeding = sortedEvents.find { it.eventType == EventData.EventType.Feeding }

        val lastDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, predictCount)
        }

        val wateringPrediction = predictFutureEvents(
            lastWatering?.eventTime?.time,
            EventData.EventType.Watering,
            plantData.wateringSummer,
            plantData.wateringWinter,
            lastDay,
            plantData.id
        )

        val sprayingPrediction = predictFutureEvents(
            lastSpraying?.eventTime?.time,
            EventData.EventType.Spraying,
            plantData.sprayingSummer,
            plantData.sprayingWinter,
            lastDay,
            plantData.id
        )

        val feedingPrediction = predictFutureEvents(
            lastFeeding?.eventTime?.time,
            EventData.EventType.Feeding,
            plantData.feedingSummer,
            plantData.feedingWinter,
            lastDay,
            plantData.id
        )

        val result = mutableListOf<EventData>()
        result.addAll(wateringPrediction)
        result.addAll(sprayingPrediction)
        result.addAll(feedingPrediction)
        return result.sortedByDescending { it.eventTime.time }
    }

    private fun predictFutureEvents(
        lastEventTime: Date?,
        eventType: EventData.EventType,
        eventPeriodSummer: Int,
        eventPeriodWinter: Int,
        lastPredictionDay: Calendar,
        plantId: String
    ): List<EventData> {

        val eventPrediction = mutableListOf<EventData>()

        val predictionStartTime = if (lastEventTime != null) {
            val nextEventPredictions = Calendar.getInstance().apply {
                timeInMillis = lastEventTime.time
                add(
                    Calendar.DAY_OF_YEAR, if (isSummerSeason()) eventPeriodSummer else eventPeriodWinter
                )
            }
            if (nextEventPredictions.timeInMillis > System.currentTimeMillis()) lastEventTime
            else {
                eventPrediction.add(EventData(null, false, Calendar.getInstance(), eventType, plantId))
                Date()
            }
        } else {
            eventPrediction.add(EventData(null, false, Calendar.getInstance(), eventType, plantId))
            Date()
        }

        val currentDate = Calendar.getInstance().apply {
            time = predictionStartTime
        }

        while (currentDate.timeInMillis < lastPredictionDay.timeInMillis) {
            currentDate.add(
                Calendar.DAY_OF_YEAR, if (currentDate.isSummerSeason()) eventPeriodSummer else eventPeriodWinter
            )
            eventPrediction.add(EventData(null, false, Calendar.getInstance().apply {
                timeInMillis = currentDate.timeInMillis
            }, eventType, plantId))
        }

        return eventPrediction
    }
}