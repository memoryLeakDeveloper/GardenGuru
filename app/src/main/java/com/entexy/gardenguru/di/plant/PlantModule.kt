package com.entexy.gardenguru.di.plant

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.plant.PlantRepositoryImpl
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.*
import com.entexy.gardenguru.data.plant.pest.PestsCloudDataSource
import com.entexy.gardenguru.domain.repository.PlantRepository
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
        plantCloudDataSource: PlantCloudDataSource,
        pestsCloudDataSource: PestsCloudDataSource,
        benefitsCloudDataSource: BenefitsCloudDataSource,
        searchPlantDataSource: SearchPlantDataSource,
        deletePlantDataSource: DeletePlantDataSource,
        updatePlantCustomPhotoDataSource: UpdatePlantCustomPhotoDataSource,
        renamePlantDataSource: RenamePlantDataSource,
        addPlantDataSource: AddPlantDataSource,
        userPlantsDataSource: UserPlantsDataSource
    ): PlantRepository = PlantRepositoryImpl(
        plantCloudDataSource,
        pestsCloudDataSource,
        benefitsCloudDataSource,
        searchPlantDataSource,
        deletePlantDataSource,
        renamePlantDataSource,
        updatePlantCustomPhotoDataSource,
        addPlantDataSource,
        userPlantsDataSource
    )

    @Provides
    @Singleton
    fun providePlantCloudDataSource(): PlantCloudDataSource = PlantCloudDataSource.Base(App.firestoreUserPlantRef!!)


    @Provides
    @Singleton
    fun providePestsCloudDataSource(): PestsCloudDataSource = PestsCloudDataSource.Base(App.firestorePestsRef)


    @Provides
    @Singleton
    fun provideBenefitsCloudDataSource(): BenefitsCloudDataSource = BenefitsCloudDataSource.Base(App.firestoreBenefitsRef)

    @Provides
    @Singleton
    fun provideDeletePlantDataSource(): DeletePlantDataSource = DeletePlantDataSource.Base(App.firestoreUserPlantRef!!)


    @Provides
    @Singleton
    fun provideDeletePlantPhotoDataSource(): UpdatePlantCustomPhotoDataSource =
        UpdatePlantCustomPhotoDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    @Singleton
    fun provideRenamePlantDataSource(): RenamePlantDataSource = RenamePlantDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    @Singleton
    fun provideAddPlantDataSource(): AddPlantDataSource = AddPlantDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    @Singleton
    fun provideSearchPlantCloudDataSource(): SearchPlantDataSource = SearchPlantDataSource.Base(App.firestorePlantsRef)

    @Provides
    fun provideUserPlantsDataSource(): UserPlantsDataSource = UserPlantsDataSource.Base(App.firestoreUsersRef)

}