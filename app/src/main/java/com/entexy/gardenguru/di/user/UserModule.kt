package com.entexy.gardenguru.di.user

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.user.UserRepositoryImpl
import com.entexy.gardenguru.data.user.cloud.*
import com.entexy.gardenguru.domain.repository.UserRepository
import com.entexy.gardenguru.domain.usecases.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    fun provideCreateUserDataSource(): CreateUserDataSource = CreateUserDataSource.Base(App.firestoreUsersRef)

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
    fun provideLoginUserDataSource(): LoginUserDataSource = LoginUserDataSource.Base(App.firebaseAuth)

    @Provides
    fun provideLoginUserUseCase(repository: UserRepository): LoginUserUseCase = LoginUserUseCase(repository)

    @Provides
    fun provideUserPlantsDataSource(): UserPlantsDataSource = UserPlantsDataSource.Base(App.firestoreUsersRef)

    @Provides
    fun provideFetchUserPlantsUseCase(repository: UserRepository): FetchUserPlantsUseCase = FetchUserPlantsUseCase(repository)


    @Provides
    fun provideUserRepository(
        loginUserDataSource: LoginUserDataSource,
        createUserDataSource: CreateUserDataSource,
        deleteUserDataSource: DeleteUserDataSource,
        signOutUserDataSource: SignOutUserDataSource,
        userPlantsDataSource: UserPlantsDataSource
    ): UserRepository =
        UserRepositoryImpl(loginUserDataSource, createUserDataSource, deleteUserDataSource, signOutUserDataSource, userPlantsDataSource)

}