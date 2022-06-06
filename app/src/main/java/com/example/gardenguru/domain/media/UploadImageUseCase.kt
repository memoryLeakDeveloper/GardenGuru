package com.example.gardenguru.domain.media

import android.content.Context
import android.net.Uri
import com.example.gardenguru.data.media.MediaRepository
import com.example.gardenguru.data.plant.PlantRepository
import com.example.gardenguru.data.plant.cloud.create.CreatePlantBody
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: MediaRepository) {
    suspend fun uploadImage(uri: Uri, type: String, context: Context) =
        repository.uploadPhoto(uri, type, context)
}