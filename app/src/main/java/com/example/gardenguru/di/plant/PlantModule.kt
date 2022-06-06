package com.example.gardenguru.di.plant

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.plant.PlantRepository
import com.example.gardenguru.data.plant.cloud.create.CreatePlantService
import com.example.gardenguru.data.plant.cloud.create.CreatePlantSource
import com.example.gardenguru.domain.plant.CreatePlantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantModule {

    @Provides
    fun providePlantRepository(
        tokenHelper: TokenHelper.Base,
        createPlantService: CreatePlantService
    ): PlantRepository = PlantRepository.Base(
        tokenHelper,
        CreatePlantSource.Base(createPlantService)
    )

    @Provides
    @Singleton
    fun provideCreatePlantService(api: Api): CreatePlantService = api.makeService(CreatePlantService::class.java)

    @Provides
    fun provideCreatePlantUseCase(plantRepository: PlantRepository): CreatePlantUseCase =
        CreatePlantUseCase(plantRepository)
}