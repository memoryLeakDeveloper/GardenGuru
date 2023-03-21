package com.entexy.gardenguru.data.pest

import com.entexy.gardenguru.data.pest.cloud.PestCloud
import com.entexy.gardenguru.domain.repository.PestRepository
import javax.inject.Inject

class PestRepositoryImpl @Inject constructor() : PestRepository {

    override suspend fun fetchPest(idPest: String): PestCloud {
        //TODO
        return PestCloud("3423", "23423")
    }

}