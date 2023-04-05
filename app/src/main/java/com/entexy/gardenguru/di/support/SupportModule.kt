package com.entexy.gardenguru.di.support

import com.entexy.gardenguru.core.SupportApi
import com.entexy.gardenguru.data.support.SupportRepositoryImpl
import com.entexy.gardenguru.data.support.cloud.SendFeedbackDataSource
import com.entexy.gardenguru.data.support.cloud.SendFeedbackService
import com.entexy.gardenguru.data.support.cloud.SendMediaService
import com.entexy.gardenguru.domain.repository.SupportRepository
import com.entexy.gardenguru.domain.usecases.support.SendFeedbackUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SupportModule {

    @Provides
    fun provideSendFeedbackUseCase(repository: SupportRepository): SendFeedbackUseCase = SendFeedbackUseCase(repository)

    @Provides
    fun provideSupportRepository(
        sendFeedbackDataSource: SendFeedbackDataSource
    ): SupportRepository = SupportRepositoryImpl(sendFeedbackDataSource)

    @Provides
    fun provideSendFeedbackDataSource(api: SupportApi): SendFeedbackDataSource = SendFeedbackDataSource.Base(
        api.makeService(SendFeedbackService::class.java),
        api.makeService(SendMediaService::class.java)
    )

    @Provides
    fun provideSupportApi(): SupportApi = SupportApi()
}