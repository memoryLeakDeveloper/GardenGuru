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
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PlantModule {

    @Provides
    fun providePlantRepository(
        plantCloudDataSource: PlantCloudDataSource,
        pestsCloudDataSource: PestsCloudDataSource,
        benefitsCloudDataSource: BenefitsCloudDataSource,
        searchPlantDataSource: SearchPlantDataSource,
        deletePlantDataSource: DeletePlantDataSource,
        updatePlantCustomPhotoDataSource: UpdatePlantCustomPhotoDataSource,
        renamePlantDataSource: RenamePlantDataSource,
        addPlantDataSource: AddPlantDataSource,
    ): PlantRepository = PlantRepositoryImpl(
        plantCloudDataSource,
        pestsCloudDataSource,
        benefitsCloudDataSource,
        searchPlantDataSource,
        deletePlantDataSource,
        renamePlantDataSource,
        updatePlantCustomPhotoDataSource,
        addPlantDataSource
    )

    @Provides
    fun providePestsCloudDataSource(): PestsCloudDataSource = PestsCloudDataSource.Base(App.firestorePestsRef)


    @Provides
    fun provideBenefitsCloudDataSource(): BenefitsCloudDataSource = BenefitsCloudDataSource.Base(App.firestoreBenefitsRef)

    @Provides
    fun provideDeletePlantDataSource(): DeletePlantDataSource = DeletePlantDataSource.Base(App.firestoreUserPlantRef!!)


    @Provides
    fun provideDeletePlantPhotoDataSource(): UpdatePlantCustomPhotoDataSource =
        UpdatePlantCustomPhotoDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    fun provideRenamePlantDataSource(): RenamePlantDataSource = RenamePlantDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    fun provideAddPlantDataSource(): AddPlantDataSource = AddPlantDataSource.Base(App.firestoreUserPlantRef!!)

    @Provides
    fun provideSearchPlantCloudDataSource(): SearchPlantDataSource = SearchPlantDataSource.Base(App.firestorePlantsRef)

}