package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.utils.isDaysEquals
import com.entexy.gardenguru.utils.isSummerSeason
import java.lang.Integer.min
import java.util.*
import javax.inject.Inject

class PredictEventsUseCase @Inject constructor() {

    fun predictPastEvents(plantData: PlantData, events: ArrayList<EventData>): List<EventData> {
        val sortedEvents = ArrayList(events.sortedByDescending { it.eventTime.time })

        val wateringPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Watering,
            sortedEvents.filter { it.eventType == EventData.EventType.Watering },
            plantData.wateringSummer,
            plantData.wateringWinter
        )

        val sprayingPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Spraying,
            sortedEvents.filter { it.eventType == EventData.EventType.Spraying },
            plantData.sprayingSummer,
            plantData.sprayingWinter
        )

        val feedingPrediction = predictPastEvents(
            plantData.addingTime,
            EventData.EventType.Feeding,
            sortedEvents.filter { it.eventType == EventData.EventType.Feeding },
            plantData.wateringSummer,
            plantData.wateringWinter
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
                    eventType
                )
            )
        }
        currentEventDate.timeInMillis = firstPrediction.timeInMillis

        var i = 0

        val currentTime = System.currentTimeMillis()
        while (currentEventDate.timeInMillis <= currentTime) {
            val iEvent = eventPrediction.getOrNull(i)
            val daysAfterEvent = if (iEvent?.isCompleted == true) {
                    if (currentEventDate.isSummerSeason()) eventPeriodSummer else eventPeriodWinter
                } else 1

            val predictedTime = Calendar.getInstance().apply {
                timeInMillis = currentEventDate.timeInMillis
                add(Calendar.DAY_OF_YEAR, daysAfterEvent)
            }

            val iNextEvent = eventPrediction.getOrNull(i + 1)
            if (iNextEvent == null || !predictedTime.isDaysEquals(iNextEvent.eventTime)) {
                eventPrediction.add(
                    min(i + 1, eventPrediction.size - 1),
                    EventData(
                        null,
                        false,
                        Calendar.getInstance().apply { timeInMillis = predictedTime.timeInMillis },
                        eventType
                    )
                )
            }
            i++
            currentEventDate.timeInMillis = predictedTime.timeInMillis
        }
        return eventPrediction
    }

    fun predictFutureEvents(plantData: PlantData, events: ArrayList<EventData>, predictCount: Int): List<EventData> {
        val sortedEvents = events.sortedByDescending { it.eventTime.time }


        val lastWatering = sortedEvents.find { it.eventType == EventData.EventType.Watering }

        val lastSpraying = sortedEvents.find { it.eventType == EventData.EventType.Spraying }

        val lastFeeding = sortedEvents.find { it.eventType == EventData.EventType.Feeding }

        val lastDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, predictCount)
        }

        val wateringPrediction = predictFutureEvents(
            lastWatering?.eventTime?.time, EventData.EventType.Watering, plantData.wateringSummer, plantData.wateringWinter, lastDay
        )

        val sprayingPrediction = predictFutureEvents(
            lastSpraying?.eventTime?.time, EventData.EventType.Spraying, plantData.sprayingSummer, plantData.sprayingWinter, lastDay
        )

        val feedingPrediction = predictFutureEvents(
            lastFeeding?.eventTime?.time, EventData.EventType.Feeding, plantData.feedingSummer, plantData.feedingWinter, lastDay
        )

        val result = mutableListOf<EventData>()
        result.addAll(wateringPrediction)
        result.addAll(sprayingPrediction)
        result.addAll(feedingPrediction)
        return result.sortedByDescending { it.eventTime.time }
    }

    private fun predictFutureEvents(
        lastEventTime: Date?, eventType: EventData.EventType, eventPeriodSummer: Int, eventPeriodWinter: Int, lastPredictionDay: Calendar
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
                eventPrediction.add(EventData(null, false, Calendar.getInstance(), eventType))
                Date()
            }
        } else {
            eventPrediction.add(EventData(null, false, Calendar.getInstance(), eventType))
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
            }, eventType))
        }

        return eventPrediction
    }
}