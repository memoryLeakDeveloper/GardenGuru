package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.data.pest.cloud.PestCloud

interface PestRepository {

    suspend fun fetchPest(idPest: String): PestCloud

}