package com.entexy.gardenguru.di.user

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.user.UserRepositoryImpl
import com.entexy.gardenguru.data.user.cloud.CreateUserDataSource
import com.entexy.gardenguru.data.user.cloud.DeleteUserDataSource
import com.entexy.gardenguru.domain.repository.UserRepository
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import com.entexy.gardenguru.domain.usecases.user.DeleteUserUseCase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    fun provideCreateUserDataSource(): CreateUserDataSource = CreateUserDataSource.Base(App.firestoreUserRef)

    @Provides
    fun provideCreateUserUseCase(repository: UserRepository): CreateUserUseCase = CreateUserUseCase(repository)

    @Provides
    fun provideDeleteUserDataSource(firebaseUser: FirebaseUser): DeleteUserDataSource = DeleteUserDataSource.Base(firebaseUser)

    @Provides
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase = DeleteUserUseCase(repository)

    @Provides
    fun provideUserRepository(
        createUserDataSource: CreateUserDataSource,
        deleteUserDataSource: DeleteUserDataSource
    ): UserRepository = UserRepositoryImpl(createUserDataSource, deleteUserDataSource)

    @Singleton
    @Provides
    fun provideCurrentUser(): FirebaseUser = Firebase.auth.currentUser!!
}