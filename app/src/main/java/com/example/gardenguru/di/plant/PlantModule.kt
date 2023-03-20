package com.example.gardenguru.di.plant

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.language.LanguageHelper
import com.example.gardenguru.data.plant.PlantRepositoryImpl
import com.example.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.example.gardenguru.data.plant.cloud.PlantService
import com.example.gardenguru.data.plant.cloud.create.CreatePlantService
import com.example.gardenguru.data.plant.cloud.create.CreatePlantSource
import com.example.gardenguru.domain.repository.PlantRepository
import com.example.gardenguru.domain.usecases.plant.CreatePlantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlantModule {

    @Provides
    fun providePlantRepositoryImpl(
        tokenHelper: TokenHelper.Base,
        languageHelper: LanguageHelper.Base,
        createPlantService: CreatePlantService,
        plantService: PlantService,
    ) = PlantRepositoryImpl(
        tokenHelper,
        CreatePlantSource.Base(createPlantService),
        PlantCloudDataSource.Base(plantService),
        languageHelper
    )

    @Provides
    @Singleton
    fun providePlantService(api: Api): PlantService = api.makeService(PlantService::class.java)

    @Provides
    @Singleton
    fun provideCreatePlantService(api: Api): CreatePlantService = api.makeService(CreatePlantService::class.java)

    @Provides
    fun provideCreatePlantUseCase(plantRepository: PlantRepositoryImpl): CreatePlantUseCase = CreatePlantUseCase(plantRepository)

}