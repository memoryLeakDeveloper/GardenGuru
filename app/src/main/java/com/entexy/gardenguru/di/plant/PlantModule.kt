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
        deletePlantPhotoDataSource: DeletePlantPhotoDataSource,
        renamePlantDataSource: RenamePlantDataSource,
        addPlantDataSource: AddPlantDataSource
    ): PlantRepository = PlantRepositoryImpl(
        plantCloudDataSource,
        pestsCloudDataSource,
        benefitsCloudDataSource,
        searchPlantDataSource,
        deletePlantDataSource,
        deletePlantPhotoDataSource,
        renamePlantDataSource,
        addPlantDataSource
    )

    @Provides
    @Singleton
    fun providePlantCloudDataSource(): PlantCloudDataSource = PlantCloudDataSource.Base(App.firestorePlantsRef)


    @Provides
    @Singleton
    fun providePestsCloudDataSource(): PestsCloudDataSource = PestsCloudDataSource.Base(App.firestorePestsRef)


    @Provides
    @Singleton
    fun provideBenefitsCloudDataSource(): BenefitsCloudDataSource = BenefitsCloudDataSource.Base(App.firestoreBenefitsRef)

    @Provides
    @Singleton
    fun provideDeletePlantDataSource(): DeletePlantDataSource = DeletePlantDataSource.Base(App.firestorePlantsRef)


    @Provides
    @Singleton
    fun provideDeletePlantPhotoDataSource(): DeletePlantPhotoDataSource = DeletePlantPhotoDataSource.Base(App.firestorePlantsRef)

    @Provides
    @Singleton
    fun provideRenamePlantDataSource(): RenamePlantDataSource = RenamePlantDataSource.Base(App.firestorePlantsRef)

    @Provides
    @Singleton
    fun provideAddPlantDataSource(): AddPlantDataSource = AddPlantDataSource.Base(App.firestorePlantsRef)

    @Provides
    @Singleton
    fun provideSearchPlantCloudDataSource(): SearchPlantDataSource = SearchPlantDataSource.Base(App.firestorePlantsRef)
}