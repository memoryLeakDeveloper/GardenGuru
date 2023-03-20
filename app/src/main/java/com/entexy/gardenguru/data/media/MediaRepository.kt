package com.entexy.gardenguru.data.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.utils.Extensions.copyToFile
import java.lang.Exception
import javax.inject.Inject

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri, type: String, context: Context): PhotoData?

    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val createPlantSource: UploadImageSource
    ): MediaRepository{

        override suspend fun uploadPhoto(uri: Uri, type: String, context: Context): PhotoData? {
            val tempFile = uri.copyToFile(context)
            return try {
                createPlantSource.upload(tokenHelper.getToken(), type, tempFile)
            }catch (e: Exception){
                null
            }
        }
    }
}
