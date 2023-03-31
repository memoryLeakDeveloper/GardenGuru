package com.entexy.gardenguru.domain.usecases.user

import com.entexy.gardenguru.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun login(id: String) = runCatching {
        repository.loginUser(id)
    }.getOrNull()

}