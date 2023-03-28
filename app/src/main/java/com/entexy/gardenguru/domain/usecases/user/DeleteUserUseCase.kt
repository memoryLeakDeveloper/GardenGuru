package com.entexy.gardenguru.domain.usecases.user

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun delete() = flow {
        emit(CloudResponse.Loading())
        repository.deleteUser()
        emit(CloudResponse.Success(Unit))
    }.catch {
        emit(CloudResponse.Error(it))
    }

}