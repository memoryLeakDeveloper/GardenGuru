package com.entexy.gardenguru.di.plant

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.PlantRepositoryImpl
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.SearchPlantDataSource
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
        languageHelper: LanguageHelper,
        plantCloudDataSource: PlantCloudDataSource,
        pestsCloudDataSource: PestsCloudDataSource,
        benefitsCloudDataSource: BenefitsCloudDataSource,
        searchPlantDataSource: SearchPlantDataSource
    ): PlantRepository = PlantRepositoryImpl(
        plantCloudDataSource,
        pestsCloudDataSource,
        benefitsCloudDataSource,
        searchPlantDataSource,
        languageHelper
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
    fun provideSearchPlantCloudDataSource(): SearchPlantDataSource = SearchPlantDataSource.Base(App.firestorePlantsRef)

}