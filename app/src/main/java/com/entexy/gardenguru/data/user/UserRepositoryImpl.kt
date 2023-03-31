package com.entexy.gardenguru.data.user

import com.entexy.gardenguru.data.user.cloud.*
import com.entexy.gardenguru.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loginUserDataSource: LoginUserDataSource,
    private val createUserDataSource: CreateUserDataSource,
    private val deleteUserDataSource: DeleteUserDataSource,
    private val signOutUserDataSource: SignOutUserDataSource
) : UserRepository {

    override suspend fun loginUser(idToken: String) = loginUserDataSource.login(idToken)

    override suspend fun createUser(id: String) = createUserDataSource.create(id)

    override suspend fun deleteUser() = deleteUserDataSource.delete()

    override suspend fun signOutUser() = signOutUserDataSource.signOut()

}