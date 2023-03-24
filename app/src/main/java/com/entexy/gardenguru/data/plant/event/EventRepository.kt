package com.entexy.gardenguru.data.plant.event

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.domain.repository.EventRepository
import com.entexy.gardenguru.ui.EventMockData

class EventRepositoryImpl(
    private val languageHelper: LanguageHelper,
    private val fetchEventsDataSource: FetchEventsDataSource,
    private val completeEventsDataSource: CompleteEventsDataSource
) : EventRepository {

    override suspend fun completeEvent(event: EventData) =
        completeEventsDataSource.completeEvent(event)

    override suspend fun fetchEvents(): CloudResponse<List<EventData>> {
//        return fetchEventsDataSource.fetchEvents() //todo
        return CloudResponse.Success(EventMockData.listData)
    }


}