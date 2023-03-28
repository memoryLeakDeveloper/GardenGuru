package com.entexy.gardenguru.di.user

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.user.UserRepositoryImpl
import com.entexy.gardenguru.data.user.cloud.CreateUserDataSource
import com.entexy.gardenguru.domain.repository.UserRepository
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    fun provideCreateUserDataSource(): CreateUserDataSource = CreateUserDataSource.Base(App.firestoreUserRef)

    @Provides
    fun provideCreateUserUseCase(repository: UserRepository): CreateUserUseCase = CreateUserUseCase(repository)

    @Provides
    fun provideUserRepository(createUserDataSource: CreateUserDataSource): UserRepository = UserRepositoryImpl(createUserDataSource)

}