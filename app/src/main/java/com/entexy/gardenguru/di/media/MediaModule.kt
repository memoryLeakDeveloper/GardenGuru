package com.entexy.gardenguru.di.media

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.media.MediaRepositoryImpl
import com.entexy.gardenguru.data.media.cloud.DeleteImageSource
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.domain.repository.MediaRepository
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
        createImageSource: UploadImageSource,
        deleteImageSource: DeleteImageSource
    ): MediaRepository =
        MediaRepositoryImpl(createImageSource, deleteImageSource)

    @Provides
    fun provideUploadImageDataSource(): UploadImageSource = UploadImageSource.Base(App.storagePhotos)

    @Provides
    @Singleton
    fun provideDeleteImageSource(): DeleteImageSource = DeleteImageSource.Base(App.storagePhotos)
}