package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse

interface UserRepository {

    suspend fun createUser(id: String): CloudResponse<Unit>

}