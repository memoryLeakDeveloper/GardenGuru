package com.example.gardenguru.data.media

import android.content.Context
import android.net.Uri
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.media.cloud.UploadImageSource
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import com.example.gardenguru.utils.Extensions.copyToFile
import java.lang.Exception
import javax.inject.Inject

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri, type: String, context: Context): PhotoDataCloud?

    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val createPlantSource: UploadImageSource
    ): MediaRepository{

        override suspend fun uploadPhoto(uri: Uri, type: String, context: Context): PhotoDataCloud? {
            val tempFile = uri.copyToFile(context)
            return try {
                createPlantSource.upload(tokenHelper.getToken(), type, tempFile)
            }catch (e: Exception){
                null
            }
        }
    }
}
