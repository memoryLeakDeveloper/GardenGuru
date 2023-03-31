package com.entexy.gardenguru.data.media.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface DeleteImageSource {

    suspend fun deleteImage(
        imageUrl: String
    ): CloudResponse<Unit>

    class Base @Inject constructor(private val storageRef: StorageReference) : DeleteImageSource {
        override suspend fun deleteImage(imageUrl: String): CloudResponse<Unit> {
            val deleteTask = storageRef.storage.getReferenceFromUrl(imageUrl).delete()
            deleteTask.await()
            return if (deleteTask.isSuccessful){
                CloudResponse.Success(Unit)
            }else{
                CloudResponse.Error(deleteTask.exception)
            }
        }

    }
}