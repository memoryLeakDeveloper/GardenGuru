package com.entexy.gardenguru.domain.usecases.events

import android.util.Log
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.PredictionRepository
import javax.inject.Inject

class ExistNotCompleteEventForTodayUseCase @Inject constructor(
    private val repository: PredictionRepository,
    private val predictEventsUseCase: PredictEventsUseCase
) {
    suspend fun perform(): Boolean {
        val plants = repository.fetchPlainUserPlants()
        if (plants is CloudResponse.Success) {
            val events = repository.fetchUserEvents(plants.result.map { it.id })

            if (events is CloudResponse.Success) {
                return predictEventsUseCase.isExistNotCompletedEventToday(plants.result, events.result)
            } else {
                Log.d(
                    "qqqqq",
                    "IsExistNotCompleteEventsForTodayUseCase error while reading events: \n${(events as? CloudResponse.Error)?.exception?.stackTraceToString()}"
                )
            }
        } else {
            Log.d(
                "qqqqq",
                "IsExistNotCompleteEventsForTodayUseCase error while reading plants: \n${(plants as? CloudResponse.Error)?.exception?.stackTraceToString()}"
            )
        }
        return false
    }
}