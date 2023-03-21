package com.entexy.gardenguru.data.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.domain.repository.MediaRepository
import com.entexy.gardenguru.utils.copyToFile
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val tokenHelper: TokenHelper.Base,
    private val createPlantSource: UploadImageSource
) : MediaRepository {

    override suspend fun uploadPhoto(uri: Uri, type: String, context: Context) = runCatching {
        createPlantSource.upload(tokenHelper.getToken(), type, uri.copyToFile(context))
    }.getOrNull()

}

