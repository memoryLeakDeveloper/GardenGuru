package com.entexy.gardenguru.domain.repository

import android.graphics.Bitmap
import com.entexy.gardenguru.core.exception.CloudResponse

interface MediaRepository {

    suspend fun uploadPhoto(bitmap: Bitmap): CloudResponse<String>

    suspend fun deleteImage(imageUrl: String): CloudResponse<Unit>

}