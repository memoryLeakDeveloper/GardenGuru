package com.entexy.gardenguru.data.media.cloud

import java.io.File
import javax.inject.Inject

interface UploadImageSource {

    suspend fun upload(
        token: String,
        type: String,
        file: File
    ): String

    class Base @Inject constructor() : UploadImageSource {
        override suspend fun upload(
            token: String,
            type: String,
            file: File
        ): String {
            TODO()
        }
    }
}