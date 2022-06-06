package com.example.gardenguru.di.garden

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.garden.GardenMapper
import com.example.gardenguru.data.garden.GardenRepository
import com.example.gardenguru.data.garden.cloud.create.CreateGardenService
import com.example.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.example.gardenguru.data.garden.cloud.edit.EditGardenService
import com.example.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.example.gardenguru.data.garden.cloud.get.GardensDataSource
import com.example.gardenguru.data.garden.cloud.get.GetGardensService
import com.example.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.example.gardenguru.data.garden.cloud.names.GetGardenNamesService
import com.example.gardenguru.data.language.LanguageHelper
import com.example.gardenguru.domain.garden.CreateGardenUseCase
import com.example.gardenguru.domain.garden.EditGardensUseCase
import com.example.gardenguru.domain.garden.GetGardenNamesUseCase
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
        languageHelper: LanguageHelper.Base,
        getGardenService: GetGardensService,
        editGardenService: EditGardenService,
        getGardenNamesService: GetGardenNamesService,
        createGardenService: CreateGardenService,
        gardenMapper: GardenMapper
    ): GardenRepository = GardenRepository.Base(
        tokenHelper,
        languageHelper,
        GardensDataSource.Base(getGardenService),
        EditGardenSource.Base(editGardenService),
        CreateGardenSource.Base(createGardenService),
        GetGardenNamesDataSource.Base(getGardenNamesService),
        gardenMapper
    )

    @Provides
    @Singleton
    fun provideMapper(): GardenMapper = GardenMapper()

    @Provides
    @Singleton
    fun provideGardenService(api: Api): GetGardensService = api.makeServiceWithCashing(GetGardensService::class.java)

    @Provides
    @Singleton
    fun provideEditGardenService(api: Api): EditGardenService = api.makeService(EditGardenService::class.java)

    @Provides
    @Singleton
    fun provideGetGardenNamesService(api: Api): GetGardenNamesService = api.makeService(GetGardenNamesService::class.java)

    @Provides
    @Singleton
    fun provideCreateGardenService(api: Api): CreateGardenService = api.makeService(CreateGardenService::class.java)

    @Provides
    fun provideGetGardensUseCase(gardenRepository: GardenRepository): GetGardensUseCase =
        GetGardensUseCase(gardenRepository)


    @Provides
    fun provideEditGardensUseCase(gardenRepository: GardenRepository): EditGardensUseCase =
        EditGardensUseCase(gardenRepository)

    @Provides
    fun provideGetGardenNamesUseCase(gardenRepository: GardenRepository): GetGardenNamesUseCase =
        GetGardenNamesUseCase(gardenRepository)

    @Provides
    fun provideCreateGardenUseCase(gardenRepository: GardenRepository): CreateGardenUseCase =
        CreateGardenUseCase(gardenRepository)
}