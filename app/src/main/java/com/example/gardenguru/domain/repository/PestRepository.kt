package com.example.gardenguru.domain.repository

import com.example.gardenguru.data.pest.cloud.PestCloud

interface PestRepository {

    suspend fun fetchPest(idPest: String): PestCloud

}