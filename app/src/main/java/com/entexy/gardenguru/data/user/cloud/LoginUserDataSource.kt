package com.entexy.gardenguru.data.user.cloud

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

interface LoginUserDataSource {

    suspend fun login(idToken: String): FirebaseUser?

    class Base(private val firebaseAuth: FirebaseAuth) : LoginUserDataSource {

        override suspend fun login(idToken: String): FirebaseUser? {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val task = firebaseAuth.signInWithCredential(credential)
            task.await()
            return if (task.exception == null) {
                task.result.user
            } else null
        }
    }

}