package com.entexy.gardenguru.data.media.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import javax.inject.Inject

interface DeleteImageSource {

    suspend fun deleteImage(
        imageUrl: String
    ): CloudResponse<Unit>

    class Base @Inject constructor() : DeleteImageSource {
        override suspend fun deleteImage(imageUrl: String): CloudResponse<Unit> {
            //TODO("Not yet implemented")
            return CloudResponse.Success(Unit)
        }

    }
}