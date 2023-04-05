package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.MediaRepository
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePlantPhotoUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val mediaRepository: MediaRepository
    ) {
    suspend fun perform(plantId: String, imageUrl: String): Flow<CloudResponse<Unit>> = flow {
        emit(CloudResponse.Loading())

        val result = mediaRepository.deleteImage(imageUrl)
        if (result is CloudResponse.Success) {
            emit(plantRepository.updatePlantCustomPhoto(plantId, null))
        } else emit(CloudResponse.Error(null))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}