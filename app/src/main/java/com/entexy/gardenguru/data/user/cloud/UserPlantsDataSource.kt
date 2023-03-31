package com.entexy.gardenguru.data.user.cloud

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface UserPlantsDataSource {

    suspend fun fetch(uid: String): List<String>?

    class Base(private val firestoreUsersRef: CollectionReference) : UserPlantsDataSource {

        override suspend fun fetch(uid: String): List<String>? {
            val task = firestoreUsersRef.document(uid).collection("plants").get()
            task.await()
            return if (task.exception == null) {
                task.result.map { it.id }
            } else null
        }
    }

}