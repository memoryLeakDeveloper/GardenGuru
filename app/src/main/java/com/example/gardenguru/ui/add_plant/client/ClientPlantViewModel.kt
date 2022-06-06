package com.example.gardenguru.ui.add_plant.client

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import com.example.gardenguru.domain.media.UploadImageUseCase
import javax.inject.Inject

class ClientPlantViewModel(private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    private var plantImage: PhotoDataCloud? = null

    suspend fun uploadImage(uri: Uri, imageType: String, context: Context): PhotoDataCloud? {
        plantImage = uploadImageUseCase.uploadImage(uri, imageType, context)
        return plantImage
    }

    class Factory @Inject constructor(
        private val uploadImageUseCase: UploadImageUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClientPlantViewModel(uploadImageUseCase) as T
        }
    }
}