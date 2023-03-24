package com.entexy.gardenguru.di.events

import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.cloud.*
import com.entexy.gardenguru.data.plant.event.EventRepositoryImpl
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventsModule {

    @Provides
    fun provideEventRepository(
        languageHelper: LanguageHelper,
        fetchEventsDataSource: FetchEventsDataSource,
        completeEventsDataSource: CompleteEventsDataSource
    ): EventRepository = EventRepositoryImpl(
        languageHelper,
        fetchEventsDataSource,
        completeEventsDataSource
    )

    @Provides
    @Singleton
    fun provideFetchEventsDataSource(): FetchEventsDataSource = FetchEventsDataSource.Base()

    @Provides
    @Singleton
    fun provideCompleteEventsDataSource(): CompleteEventsDataSource = CompleteEventsDataSource.Base()
}