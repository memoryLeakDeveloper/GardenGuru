package com.entexy.gardenguru.domain.usecases.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.data.media.MediaRepositoryImpl
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: MediaRepositoryImpl) {

    suspend fun uploadImage(uri: Uri, type: String, context: Context) = repository.uploadPhoto(uri, type, context)

}