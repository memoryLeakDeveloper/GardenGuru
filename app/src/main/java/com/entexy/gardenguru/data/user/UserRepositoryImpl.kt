package com.entexy.gardenguru.data.user

import com.entexy.gardenguru.data.user.cloud.CreateUserDataSource
import com.entexy.gardenguru.data.user.cloud.DeleteUserDataSource
import com.entexy.gardenguru.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val createUserDataSource: CreateUserDataSource,
    private val deleteUserDataSource: DeleteUserDataSource
) : UserRepository {

    override suspend fun createUser(id: String) = createUserDataSource.create(id)

    override suspend fun deleteUser() = deleteUserDataSource.delete()

}