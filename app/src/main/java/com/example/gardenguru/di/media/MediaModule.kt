package com.example.gardenguru.di.media

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.media.MediaRepository
import com.example.gardenguru.data.media.cloud.UploadImageService
import com.example.gardenguru.data.media.cloud.UploadImageSource
import com.example.gardenguru.data.plant.PlantRepository
import com.example.gardenguru.data.plant.cloud.create.CreatePlantService
import com.example.gardenguru.data.plant.cloud.create.CreatePlantSource
import com.example.gardenguru.domain.media.UploadImageUseCase
import com.example.gardenguru.domain.plant.CreatePlantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    fun provideMediaRepository(
        tokenHelper: TokenHelper.Base,
        uploadImageService: UploadImageService
    ): MediaRepository = MediaRepository.Base(
        tokenHelper,
        UploadImageSource.Base(uploadImageService)
    )

    @Provides
    @Singleton
    fun provideUploadImageService(api: Api): UploadImageService = api.makeService(UploadImageService::class.java)

    @Provides
    fun provideUploadImageUseCase(mediaRepository: MediaRepository): UploadImageUseCase =
        UploadImageUseCase(mediaRepository)
}