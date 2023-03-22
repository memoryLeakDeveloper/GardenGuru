package com.entexy.gardenguru.ui.fragments.add_plant.client

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.media.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientPlantViewModel @Inject constructor(private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    var plantImage: String? = null

    suspend fun uploadImage(uri: Uri, imageType: String, context: Context) = uploadImageUseCase.uploadImage(uri, imageType, context)

}