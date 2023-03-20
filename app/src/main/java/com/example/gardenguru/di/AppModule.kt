package com.example.gardenguru.di

import android.content.Context
import com.example.gardenguru.core.Api
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.auth.UserEmailHelper
import com.example.gardenguru.data.language.LanguageHelper
import com.example.gardenguru.domain.usecases.app.UserEmailUseCase
import com.example.gardenguru.utils.getPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApi() = Api()

    @Provides
    @Singleton
    fun provideTokenHelper(@ApplicationContext context: Context) = TokenHelper.Base(context.getPrefs())

    @Provides
    @Singleton
    fun provideLanguageHelper(@ApplicationContext context: Context) = LanguageHelper.Base(context.getPrefs())


    @Provides
    @Singleton
    fun provideEmailUseCase(@ApplicationContext context: Context) = UserEmailUseCase(UserEmailHelper.Base(context.getPrefs()))

}