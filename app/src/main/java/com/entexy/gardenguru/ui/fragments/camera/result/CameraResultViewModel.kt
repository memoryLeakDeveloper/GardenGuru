package com.entexy.gardenguru.ui.fragments.camera.result

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.domain.usecases.plant.RecognizePlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.SearchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraResultViewModel @Inject constructor(
    private val recognizePlantUseCase: RecognizePlantUseCase,
    private val searchPlantUseCase: SearchPlantUseCase,
) : ViewModel() {

    suspend fun processImage(context: Context, bitmap: Bitmap): List<PlantData> {
        val recognitionResult = recognizePlantUseCase.recognizePlant(context, bitmap)
        return searchPlantUseCase.searchPlant(recognitionResult)
    }

}
