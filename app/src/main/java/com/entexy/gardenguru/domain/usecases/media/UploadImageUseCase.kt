package com.entexy.gardenguru.domain.usecases.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.data.media.MediaRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: MediaRepository) {
    suspend fun uploadImage(uri: Uri, type: String, context: Context) =
        repository.uploadPhoto(uri, type, context)
}