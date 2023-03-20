package com.entexy.gardenguru.data.pest

import com.entexy.gardenguru.data.pest.cloud.PestCloud
import javax.inject.Inject

interface PestRepository {

    suspend fun fetchPest(idPest: String) : PestCloud

    class Base @Inject constructor(): PestRepository {

        override suspend fun fetchPest(idPest: String): PestCloud {
            TODO("Not yet implemented")
        }
    }
}