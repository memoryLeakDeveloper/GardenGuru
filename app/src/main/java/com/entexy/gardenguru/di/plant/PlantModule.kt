package com.entexy.gardenguru.di.plant

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.PlantRepositoryImpl
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.PlantService
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantService
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantSource
import com.entexy.gardenguru.domain.usecases.plant.CreatePlantUseCase
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
        tokenHelper: TokenHelper,
        languageHelper: LanguageHelper,
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