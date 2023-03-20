package com.entexy.gardenguru.di.garden

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.garden.GardenMapper
import com.entexy.gardenguru.data.garden.GardenRepository
import com.entexy.gardenguru.data.garden.cloud.create.CreateGardenService
import com.entexy.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.entexy.gardenguru.data.garden.cloud.delete.DeleteGardenService
import com.entexy.gardenguru.data.garden.cloud.delete.DeleteGardenSource
import com.entexy.gardenguru.data.garden.cloud.edit.EditGardenService
import com.entexy.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.entexy.gardenguru.data.garden.cloud.get.GardensDataSource
import com.entexy.gardenguru.data.garden.cloud.get.GetGardensService
import com.entexy.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.entexy.gardenguru.data.garden.cloud.names.GetGardenNamesService
import com.entexy.gardenguru.data.garden.cloud.participants.add.AddParticipantService
import com.entexy.gardenguru.data.garden.cloud.participants.add.AddParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantService
import com.entexy.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleService
import com.entexy.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleSource
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.domain.usecases.garden.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GardenModule {

    @Provides
    fun provideGardenRepository(
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
        gardenMapper: GardenMapper
    ): GardenRepository = GardenRepository.Base(
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
        gardenMapper
    )

    @Provides
    @Singleton
    fun provideMapper(): GardenMapper = GardenMapper()

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
    fun provideGetGardensUseCase(gardenRepository: GardenRepository): GetGardensUseCase =
        GetGardensUseCase(gardenRepository)


    @Provides
    fun provideEditGardensUseCase(gardenRepository: GardenRepository): EditGardensUseCase =
        EditGardensUseCase(gardenRepository)

    @Provides
    fun provideEditParticipantRoleUseCase(gardenRepository: GardenRepository): EditParticipantRoleUseCase =
        EditParticipantRoleUseCase(gardenRepository)

    @Provides
    fun provideGetGardenNamesUseCase(gardenRepository: GardenRepository): GetGardenNamesUseCase =
        GetGardenNamesUseCase(gardenRepository)

    @Provides
    fun provideCreateGardenUseCase(gardenRepository: GardenRepository): CreateGardenUseCase =
        CreateGardenUseCase(gardenRepository)
}