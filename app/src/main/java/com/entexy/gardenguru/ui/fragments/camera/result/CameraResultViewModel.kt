package com.entexy.gardenguru.ui.fragments.camera.result

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.plant.RecognizePlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraResultViewModel @Inject constructor(private val recognizePlantUseCase: RecognizePlantUseCase) : ViewModel() {

    suspend fun processImage(context: Context, bitmap: Bitmap) = recognizePlantUseCase.recognizePlant(context, bitmap)

}
