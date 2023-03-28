package com.entexy.gardenguru.data.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.media.cloud.DeleteImageSource
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.domain.repository.MediaRepository
import com.entexy.gardenguru.utils.copyToFile
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val createPlantSource: UploadImageSource,
    private val deleteImageDataSource: DeleteImageSource
) : MediaRepository {

    override suspend fun uploadPhoto(uri: Uri, context: Context): CloudResponse<String> =
        createPlantSource.upload(uri.copyToFile(context))

    override suspend fun deleteImage(imageUrl: String): CloudResponse<Unit> {
        return deleteImageDataSource.deleteImage(imageUrl)
    }

}

