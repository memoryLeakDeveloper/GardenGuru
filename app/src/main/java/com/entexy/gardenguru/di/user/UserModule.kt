package com.entexy.gardenguru.di.user

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.user.UserRepositoryImpl
import com.entexy.gardenguru.data.user.cloud.CreateUserDataSource
import com.entexy.gardenguru.data.user.cloud.DeleteUserDataSource
import com.entexy.gardenguru.data.user.cloud.SignOutUserDataSource
import com.entexy.gardenguru.domain.repository.UserRepository
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import com.entexy.gardenguru.domain.usecases.user.DeleteUserUseCase
import com.entexy.gardenguru.domain.usecases.user.SignOutUserUseCase
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
    fun provideDeleteUserDataSource(): DeleteUserDataSource = DeleteUserDataSource.Base()

    @Provides
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase = DeleteUserUseCase(repository)

    @Provides
    fun provideSignOutUserDataSource(): SignOutUserDataSource = SignOutUserDataSource.Base()

    @Provides
    fun provideSignOutUserUseCase(repository: UserRepository): SignOutUserUseCase = SignOutUserUseCase(repository)

    @Provides
    fun provideUserRepository(
        createUserDataSource: CreateUserDataSource,
        deleteUserDataSource: DeleteUserDataSource,
        signOutUserDataSource: SignOutUserDataSource
    ): UserRepository = UserRepositoryImpl(createUserDataSource, deleteUserDataSource, signOutUserDataSource)

}