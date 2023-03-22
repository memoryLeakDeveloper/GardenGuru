package com.entexy.gardenguru.di.media

import com.entexy.gardenguru.data.media.MediaRepositoryImpl
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
    fun provideUploadImageUseCase(mediaRepository: MediaRepositoryImpl): UploadImageUseCase = UploadImageUseCase(mediaRepository)

    @Provides
    @Singleton
    fun provideUploadImageDataSource(): UploadImageSource = UploadImageSource.Base()
}