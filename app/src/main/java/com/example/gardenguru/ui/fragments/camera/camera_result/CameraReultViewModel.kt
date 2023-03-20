package com.example.gardenguru.ui.fragments.camera.camera_result

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CameraResultViewModel @Inject constructor() : ViewModel() {

    suspend fun processImage(uriResult: Uri) {
        delay(1000)
    }

}
