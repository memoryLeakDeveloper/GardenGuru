package com.entexy.gardenguru.di.recognition

import com.entexy.gardenguru.data.recognition.RecognitionRepositoryImpl
import com.entexy.gardenguru.domain.repository.RecognitionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RecognitionModule {

    @Provides
    fun provideRecognitionRepository(): RecognitionRepository = RecognitionRepositoryImpl()
}