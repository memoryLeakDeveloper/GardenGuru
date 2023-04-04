package com.entexy.gardenguru.di.events

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.plant.cloud.*
import com.entexy.gardenguru.data.plant.event.EventRepositoryImpl
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchPlantEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchUserEventsDataSource
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
        fetchPlantEventsDataSource: FetchPlantEventsDataSource,
        fetchEventsDataSource: FetchEventsDataSource,
        completeEventsDataSource: CompleteEventsDataSource,
        fetchUserEventsDataSource: FetchUserEventsDataSource,
    ): EventRepository = EventRepositoryImpl(
        fetchPlantEventsDataSource,
        fetchEventsDataSource,
        completeEventsDataSource,
        fetchUserEventsDataSource
    )

    @Provides
    @Singleton
    fun provideFetchUserEventsDataSource(): FetchUserEventsDataSource = FetchUserEventsDataSource.Base()

    @Provides
    @Singleton
    fun provideFetchPlantEventsDataSource(): FetchPlantEventsDataSource = FetchPlantEventsDataSource.Base()

    @Provides
    @Singleton
    fun provideFetchEventsDataSource(): FetchEventsDataSource = FetchEventsDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    @Singleton
    fun provideCompleteEventsDataSource(): CompleteEventsDataSource = CompleteEventsDataSource.Base(App.firestoreUserPlantRef!!)
}