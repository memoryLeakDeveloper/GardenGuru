package com.entexy.gardenguru.domain.usecases.media

import android.graphics.Bitmap
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.MediaRepository
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadPlantImageUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val mediaRepository: MediaRepository
) {
    suspend fun uploadImage(bitmap: Bitmap, plantId: String, oldImageUlr: String?) = flow {
        emit(CloudResponse.Loading())
        val uploadPhotoResult = mediaRepository.uploadPhoto(bitmap)

        if (uploadPhotoResult is CloudResponse.Success) {
            plantRepository.updatePlantCustomPhoto(plantId, uploadPhotoResult.result)
            emit(CloudResponse.Success(uploadPhotoResult.result))
            if (oldImageUlr != null) {
                mediaRepository.deleteImage(oldImageUlr)
            }
        } else {
            emit(CloudResponse.Error((uploadPhotoResult as? CloudResponse.Error)?.exception))
        }
    }.catch {
        emit(CloudResponse.Error(it))
    }
}