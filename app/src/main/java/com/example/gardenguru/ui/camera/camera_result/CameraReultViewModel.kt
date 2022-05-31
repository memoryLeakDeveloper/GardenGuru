package com.example.gardenguru.ui.camera.camera_result

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraResultViewModel : ViewModel() {

    suspend fun processImage(uriResult: Uri) {
        delay(1000)
    }

}
