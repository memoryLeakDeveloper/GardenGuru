package com.example.gardenguru.di

import android.content.Context
import android.content.SharedPreferences
import com.example.gardenguru.core.Api
import com.example.gardenguru.core.App
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.auth.UserEmailHelper
import com.example.gardenguru.domain.app.UserEmailUseCase
import com.example.gardenguru.utils.Extensions.getPrefs
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
    fun provideApi(@ApplicationContext context: Context) = Api(context)

    @Provides
    @Singleton
    fun provideTokenHelper(@ApplicationContext context: Context) = TokenHelper.Base(context.getPrefs())

    @Provides
    @Singleton
    fun provideEmailUseCase(@ApplicationContext context: Context) = UserEmailUseCase(UserEmailHelper.Base(context.getPrefs()))

}