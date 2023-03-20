package com.example.gardenguru.di.garden

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.garden.GardenRepositoryImpl
import com.example.gardenguru.data.garden.cloud.create.CreateGardenService
import com.example.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.example.gardenguru.data.garden.cloud.delete.DeleteGardenService
import com.example.gardenguru.data.garden.cloud.delete.DeleteGardenSource
import com.example.gardenguru.data.garden.cloud.edit.EditGardenService
import com.example.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.example.gardenguru.data.garden.cloud.get.GardensDataSource
import com.example.gardenguru.data.garden.cloud.get.GetGardensService
import com.example.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.example.gardenguru.data.garden.cloud.names.GetGardenNamesService
import com.example.gardenguru.data.garden.cloud.participants.add.AddParticipantService
import com.example.gardenguru.data.garden.cloud.participants.add.AddParticipantSource
import com.example.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantService
import com.example.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantSource
import com.example.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleService
import com.example.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleSource
import com.example.gardenguru.data.language.LanguageHelper
import com.example.gardenguru.domain.repository.GardenRepository
import com.example.gardenguru.domain.usecases.garden.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GardenModule {

    @Provides
    fun provideGardenRepositoryImpl(
        tokenHelper: TokenHelper.Base,
        languageHelper: LanguageHelper.Base,
        getGardenService: GetGardensService,
        editGardenService: EditGardenService,
        editParticipantRoleService: EditParticipantRoleService,
        addParticipantService: AddParticipantService,
        deleteParticipantService: DeleteParticipantService,
        getGardenNamesService: GetGardenNamesService,
        createGardenService: CreateGardenService,
        deleteGardenService: DeleteGardenService,
    ): GardenRepositoryImpl = GardenRepositoryImpl(
        tokenHelper,
        languageHelper,
        GardensDataSource.Base(getGardenService),
        EditGardenSource.Base(editGardenService),
        EditParticipantRoleSource.Base(editParticipantRoleService),
        AddParticipantSource.Base(addParticipantService),
        DeleteParticipantSource.Base(deleteParticipantService),
        CreateGardenSource.Base(createGardenService),
        DeleteGardenSource.Base(deleteGardenService),
        GetGardenNamesDataSource.Base(getGardenNamesService),
    )

    @Provides
    @Singleton
    fun provideGardenService(api: Api): GetGardensService = api.makeService(GetGardensService::class.java)

    @Provides
    @Singleton
    fun provideEditGardenService(api: Api): EditGardenService = api.makeService(EditGardenService::class.java)

    @Provides
    @Singleton
    fun provideEditParticipantRoleService(api: Api): EditParticipantRoleService = api.makeService(EditParticipantRoleService::class.java)

    @Provides
    @Singleton
    fun provideAddParticipantService(api: Api): AddParticipantService = api.makeService(AddParticipantService::class.java)

    @Provides
    @Singleton
    fun provideDeleteParticipantService(api: Api): DeleteParticipantService = api.makeService(DeleteParticipantService::class.java)

    @Provides
    @Singleton
    fun provideDeleteGardenService(api: Api): DeleteGardenService = api.makeService(DeleteGardenService::class.java)

    @Provides
    @Singleton
    fun provideGetGardenNamesService(api: Api): GetGardenNamesService = api.makeService(GetGardenNamesService::class.java)

    @Provides
    @Singleton
    fun provideCreateGardenService(api: Api): CreateGardenService = api.makeService(CreateGardenService::class.java)

    @Provides
    fun provideGetGardensUseCase(gardenRepository: GardenRepositoryImpl): GetGardensUseCase = GetGardensUseCase(gardenRepository)


    @Provides
    fun provideEditGardensUseCase(gardenRepository: GardenRepositoryImpl): EditGardensUseCase = EditGardensUseCase(gardenRepository)

    @Provides
    fun provideEditParticipantRoleUseCase(gardenRepository: GardenRepositoryImpl): EditParticipantRoleUseCase =
        EditParticipantRoleUseCase(gardenRepository)

    @Provides
    fun provideGetGardenNamesUseCase(gardenRepository: GardenRepositoryImpl): GetGardenNamesUseCase = GetGardenNamesUseCase(gardenRepository)

    @Provides
    fun provideCreateGardenUseCase(gardenRepository: GardenRepositoryImpl): CreateGardenUseCase = CreateGardenUseCase(gardenRepository)
}