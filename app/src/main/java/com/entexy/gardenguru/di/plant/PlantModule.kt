package com.entexy.gardenguru.di.plant

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.PlantRepository
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.PlantCloudMapper
import com.entexy.gardenguru.data.plant.cloud.PlantService
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantCloudMapper
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
    fun providePlantRepository(
        tokenHelper: TokenHelper.Base,
        languageHelper: LanguageHelper.Base,
        createPlantService: CreatePlantService,
        plantService: PlantService,
        cloudMapper: PlantCloudMapper.Base
    ): PlantRepository = PlantRepository.Base(
        tokenHelper,
        CreatePlantSource.Base(createPlantService),
        PlantCloudDataSource.Base(plantService),
        cloudMapper,
        CreatePlantCloudMapper(),
        languageHelper
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