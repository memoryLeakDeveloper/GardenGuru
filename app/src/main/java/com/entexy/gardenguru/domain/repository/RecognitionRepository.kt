package com.entexy.gardenguru.domain.repository

import android.content.Context
import android.graphics.Bitmap

interface RecognitionRepository {
    fun recognizePlant(context: Context, bitmap: Bitmap): List<String>
}