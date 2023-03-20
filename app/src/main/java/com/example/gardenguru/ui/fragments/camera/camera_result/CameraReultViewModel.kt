package com.example.gardenguru.ui.fragments.camera.camera_result

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class CameraResultViewModel : ViewModel() {

    suspend fun processImage(uriResult: Uri) {
        delay(1000)
    }

}
