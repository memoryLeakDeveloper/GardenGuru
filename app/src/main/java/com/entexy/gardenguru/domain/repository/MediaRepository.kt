package com.entexy.gardenguru.domain.repository

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.core.exception.CloudResponse

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri, context: Context): CloudResponse<String>

    suspend fun deleteImage(imageUrl: String): CloudResponse<Unit>

}