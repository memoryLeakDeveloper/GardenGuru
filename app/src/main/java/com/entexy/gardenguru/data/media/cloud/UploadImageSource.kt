package com.entexy.gardenguru.data.media.cloud

import android.graphics.Bitmap
import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

interface UploadImageSource {

    suspend fun upload(
        bitmap: Bitmap
    ): CloudResponse<String>

    class Base @Inject constructor(private val storageRef: StorageReference) : UploadImageSource {
        override suspend fun upload(
            bitmap: Bitmap
        ): CloudResponse<String> {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val data = baos.toByteArray()

            val imageId = UUID.randomUUID().toString()

            val task = storageRef.child(imageId).putBytes(data)
            task.await()
            return if (task.isSuccessful) {
                val uriTask = storageRef.child(imageId).downloadUrl
                uriTask.await()
                if (uriTask.isSuccessful)
                    CloudResponse.Success(uriTask.result.toString())
                else CloudResponse.Error(uriTask.exception)
            } else CloudResponse.Error(task.exception)
        }
    }
}