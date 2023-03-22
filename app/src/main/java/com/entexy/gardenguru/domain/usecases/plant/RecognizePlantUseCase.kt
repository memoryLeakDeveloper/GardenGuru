package com.entexy.gardenguru.domain.usecases.plant

import android.content.Context
import android.graphics.Bitmap
import com.entexy.gardenguru.domain.repository.RecognitionRepository
import javax.inject.Inject

class RecognizePlantUseCase @Inject constructor(private val repository: RecognitionRepository) {
    suspend fun recognizePlant(context: Context, bitmap: Bitmap) = repository.recognizePlant(context, bitmap)
}