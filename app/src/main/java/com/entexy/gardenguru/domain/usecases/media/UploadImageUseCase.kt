package com.entexy.gardenguru.domain.usecases.media

import android.content.Context
import android.net.Uri
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.media.MediaRepositoryImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: MediaRepositoryImpl) {

    suspend fun uploadImage(uri: Uri, context: Context) = flow {
        emit(CloudResponse.Loading())
        emit(repository.uploadPhoto(uri, context))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}