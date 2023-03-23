package com.entexy.gardenguru.core

import android.app.Application
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        firestoreUserRef = Firebase.firestore.document("")
        firestorePlantsRef = Firebase.firestore.collection("plants")
        firestoreGardensRef = Firebase.firestore.collection("gardens")
        firestorePestsRef = Firebase.firestore.collection("pests")
        firestoreBenefitsRef = Firebase.firestore.collection("benefits")
    }

    companion object {
        lateinit var firestoreUserRef: DocumentReference //todo дать сылку на текущего пользователя
        lateinit var firestorePlantsRef: CollectionReference
        lateinit var firestoreGardensRef: CollectionReference
        lateinit var firestorePestsRef: CollectionReference
        lateinit var firestoreBenefitsRef: CollectionReference
        lateinit var storageRef: FirebaseStorage
    }

}