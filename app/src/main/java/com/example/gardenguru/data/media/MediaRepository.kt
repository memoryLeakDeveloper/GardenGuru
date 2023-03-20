package com.example.gardenguru.data.media

import android.content.Context
import android.net.Uri
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.media.cloud.UploadImageSource
import com.example.gardenguru.domain.repository.MediaRepository
import com.example.gardenguru.utils.copyToFile
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val tokenHelper: TokenHelper.Base,
    private val createPlantSource: UploadImageSource
) : MediaRepository {

    override suspend fun uploadPhoto(uri: Uri, type: String, context: Context) = runCatching {
        createPlantSource.upload(tokenHelper.getToken(), type, uri.copyToFile(context))
    }.getOrNull()

}

