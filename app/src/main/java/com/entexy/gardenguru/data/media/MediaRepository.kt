package com.entexy.gardenguru.data.media

import android.graphics.Bitmap
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.media.cloud.DeleteImageSource
import com.entexy.gardenguru.data.media.cloud.UploadImageSource
import com.entexy.gardenguru.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val createPlantSource: UploadImageSource,
    private val deleteImageDataSource: DeleteImageSource
) : MediaRepository {

    override suspend fun uploadPhoto(bitmap: Bitmap): CloudResponse<String> =
        createPlantSource.upload(bitmap)

    override suspend fun deleteImage(imageUrl: String): CloudResponse<Unit> {
        return deleteImageDataSource.deleteImage(imageUrl)
    }

}

