package com.entexy.gardenguru.di.prediction

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.plant.cloud.*
import com.entexy.gardenguru.data.plant.event.cloud.FetchUserEventsDataSource
import com.entexy.gardenguru.data.prediction.PredictionRepositoryImpl
import com.entexy.gardenguru.domain.repository.PredictionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PredictionModule {

    @Provides
    fun providePredictionRepository(
        plantCloudDataSource: PlantCloudDataSource,
        fetchUserEventsDataSource: FetchUserEventsDataSource,
    ): PredictionRepository = PredictionRepositoryImpl(
        plantCloudDataSource,
        fetchUserEventsDataSource
    )

    @Provides
    fun providePlantCloudDataSource(): PlantCloudDataSource = PlantCloudDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    @Singleton
    fun provideFetchUserEventsDataSource(): FetchUserEventsDataSource = FetchUserEventsDataSource.Base()


}