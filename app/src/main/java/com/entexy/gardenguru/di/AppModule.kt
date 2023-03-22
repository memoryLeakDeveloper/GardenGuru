package com.entexy.gardenguru.di

import android.content.Context
import android.content.SharedPreferences
import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.auth.UserEmailHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.prefs.FirstLaunchPref
import com.entexy.gardenguru.domain.usecases.app.UserEmailUseCase
import com.entexy.gardenguru.utils.PrefsKeys
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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideTokenHelper(sharedPreferences: SharedPreferences): TokenHelper = TokenHelper.Base(sharedPreferences)

    @Provides
    @Singleton
    fun provideLanguageHelper(sharedPreferences: SharedPreferences): LanguageHelper = LanguageHelper.Base(sharedPreferences)

    @Provides
    @Singleton
    fun provideEmailUseCase(sharedPreferences: SharedPreferences) = UserEmailUseCase(UserEmailHelper.Base(sharedPreferences))


    @Provides
    @Singleton
    fun provideFirstLaunchPref(sharedPreferences: SharedPreferences) = FirstLaunchPref.Base(sharedPreferences)


}