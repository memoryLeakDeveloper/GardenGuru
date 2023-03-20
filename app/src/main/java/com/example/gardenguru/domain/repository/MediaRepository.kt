package com.example.gardenguru.domain.repository

import android.content.Context
import android.net.Uri
import com.example.gardenguru.data.media.PhotoData

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri, type: String, context: Context): PhotoData?

}