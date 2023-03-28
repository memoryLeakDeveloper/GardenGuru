package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.media.UploadImageUseCase
import com.entexy.gardenguru.domain.usecases.plant.DeletePlantPhotoUseCase
import com.entexy.gardenguru.domain.usecases.plant.DeletePlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.RenamePlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(
    private val deletePlantUseCase: DeletePlantUseCase,
    private val renamePlantUseCase: RenamePlantUseCase,
    private val deletePlantPhotoUseCase: DeletePlantPhotoUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    suspend fun deletePlant(plantId: String) =
        deletePlantUseCase.perform(plantId)

    suspend fun setPlantName(plantId: String, plantName: String) =
        renamePlantUseCase.perform(plantId, plantName)

    suspend fun deletePhoto(plantId: String, customPhoto: String) =
        deletePlantPhotoUseCase.perform(plantId, customPhoto)

    suspend fun uploadPhoto(imageUri: Uri, context: Context) =
        uploadImageUseCase.uploadImage(imageUri, context)
}
