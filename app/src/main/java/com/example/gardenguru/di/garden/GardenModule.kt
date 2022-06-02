package com.example.gardenguru.di.garden

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.garden.GardenMapper
import com.example.gardenguru.data.garden.GardenRepository
import com.example.gardenguru.data.garden.cloud.GardenCloudDataSource
import com.example.gardenguru.data.garden.cloud.GardenService
import com.example.gardenguru.domain.garden.GetGardensUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GardenModule {

    @Provides
    fun provideGardenRepository(
        tokenHelper: TokenHelper.Base,
        gardenDataSource: GardenCloudDataSource,
        gardenMapper: GardenMapper
    ): GardenRepository = GardenRepository.Base(tokenHelper, gardenDataSource, gardenMapper)

    @Provides
    @Singleton
    fun provideMapper(): GardenMapper = GardenMapper()

    @Provides
    @Singleton
    fun provideGardenService(api: Api): GardenService = api.makeServiceWithCashing(GardenService::class.java)

    @Provides
    fun provideGardenDataSource(service: GardenService): GardenCloudDataSource = GardenCloudDataSource.Base(service)

    @Provides
    fun provideGetGardensUseCase(gardenRepository: GardenRepository): GetGardensUseCase =
        GetGardensUseCase(gardenRepository)
}