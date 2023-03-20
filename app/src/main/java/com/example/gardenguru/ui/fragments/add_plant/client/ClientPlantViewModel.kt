package com.example.gardenguru.ui.fragments.add_plant.client

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.domain.usecases.media.UploadImageUseCase
import javax.inject.Inject

class ClientPlantViewModel(private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    var plantImage: PhotoData? = null

    suspend fun uploadImage(uri: Uri, imageType: String, context: Context): PhotoData? {
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