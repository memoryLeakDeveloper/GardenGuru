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
class PlantModule {

    @Provides
    fun providePlantRepository(
        tokenHelper: TokenHelper.Base,
        languageHelper: LanguageHelper.Base,
        createPlantService: CreatePlantService,
        plantService: PlantService,
        cloudMapper: PlantCloudMapper.Base
    ): PlantRepository = PlantRepository.Base(
        tokenHelper,
        CreatePlantCloudMapper(),
        CreatePlantSource.Base(createPlantService)
    )

    @Provides
    @Singleton
    fun providePlantService(api: Api): PlantService = api.makeService(PlantService::class.java)

    @Provides
    @Singleton
    fun provideCreatePlantService(api: Api): CreatePlantService = api.makeService(CreatePlantService::class.java)

    @Provides
    fun provideCreatePlantUseCase(plantRepository: PlantRepository): CreatePlantUseCase =
        CreatePlantUseCase(plantRepository)

    @Provides
    fun providePlantCloudMapper() = PlantCloudMapper.Base()
}