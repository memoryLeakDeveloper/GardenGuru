package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.usecases.events.PredictEventsUseCase
import com.entexy.gardenguru.domain.usecases.media.UploadPlantImageUseCase
import com.entexy.gardenguru.domain.usecases.plant.DeletePlantPhotoUseCase
import com.entexy.gardenguru.domain.usecases.plant.DeletePlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.RenamePlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(
    private val deletePlantUseCase: DeletePlantUseCase,
    private val renamePlantUseCase: RenamePlantUseCase,
    private val deletePlantPhotoUseCase: DeletePlantPhotoUseCase,
    private val uploadPlantImageUseCase: UploadPlantImageUseCase,
    private val predictEventsUseCase: PredictEventsUseCase,
) : ViewModel() {

    suspend fun deletePlant(plantId: String) =
        deletePlantUseCase.perform(plantId)

    suspend fun setPlantName(plantId: String, plantName: String) =
        renamePlantUseCase.perform(plantId, plantName)

    suspend fun deletePhoto(plantId: String, customPhoto: String) =
        deletePlantPhotoUseCase.perform(plantId, customPhoto)

    fun predictEvents(plantData: PlantData, events: ArrayList<EventData>): List<EventData> =
        predictEventsUseCase.predictFutureEvents(plantData, events, 3)

    @Suppress("DEPRECATION")
    suspend fun uploadPhoto(imageUri: Uri, context: Context, plantId: String, oldImageUrl: String?): Flow<CloudResponse<String>> {

        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                imageUri
            )
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
        }

        return uploadPlantImageUseCase.uploadImage(bitmap, plantId, oldImageUrl)
    }
}

