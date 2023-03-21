package com.entexy.gardenguru.di.media

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.media.MediaRepositoryImpl
import com.entexy.gardenguru.data.media.cloud.UploadImageService
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.domain.usecases.media.UploadImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    fun provideMediaRepository(tokenHelper: TokenHelper.Base, uploadImageService: UploadImageService): MediaRepositoryImpl =
        MediaRepositoryImpl(tokenHelper, UploadImageSource.Base(uploadImageService))

    @Provides
    @Singleton
    fun provideUploadImageService(api: Api): UploadImageService = api.makeService(UploadImageService::class.java)

    @Provides
    fun provideUploadImageUseCase(mediaRepository: MediaRepositoryImpl): UploadImageUseCase = UploadImageUseCase(mediaRepository)
}