package com.entexy.gardenguru.data.user.cloud

import com.entexy.gardenguru.core.exception.CloudResponse

interface CreateUserDataSource {

    fun create(id: String): CloudResponse<Unit>

    class Base() : CreateUserDataSource {
        //todo
        override fun create(id: String): CloudResponse<Unit> {
            //todo
            return CloudResponse.Loading()
        }

    }
}