package com.entexy.gardenguru.data.media.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import java.io.File
import javax.inject.Inject

interface UploadImageSource {

    suspend fun upload(
        file: File
    ): CloudResponse<String>

    class Base @Inject constructor() : UploadImageSource {
        override suspend fun upload(
            file: File
        ): CloudResponse<String> {
            //TODO()
            return CloudResponse.Success("https://hips.hearstapps.com/hmg-prod/images/high-angle-view-of-variety-of-succulent-plants-royalty-free-image-1584462052.jpg")
        }
    }
}