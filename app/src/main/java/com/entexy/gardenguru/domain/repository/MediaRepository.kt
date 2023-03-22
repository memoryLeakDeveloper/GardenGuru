package com.entexy.gardenguru.domain.repository

import android.content.Context
import android.net.Uri

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri, type: String, context: Context): String?

}