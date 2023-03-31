package com.entexy.gardenguru.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.entexy.gardenguru.data.language.LanguagePreference
import com.entexy.gardenguru.data.user.UserData
import com.entexy.gardenguru.utils.PrefsKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        firestoreUsersRef = Firebase.firestore.collection("Users")
        firestorePlantsRef = Firebase.firestore.collection("plants")
        firestoreGardensRef = Firebase.firestore.collection("gardens")
        firestorePestsRef = Firebase.firestore.collection("pests")
        firestoreBenefitsRef = Firebase.firestore.collection("benefits")
        firebaseAuth = Firebase.auth
        storagePhotos = Firebase.storage.reference.child("plants")

        languagePreference = LanguagePreference(sharedPreferences)
        user = UserData("L7HK0VHcPaTteMoaHoWmAAM7ejy2", arrayListOf("JYgWks56qM4AL6vhGQuv", "HVWKaTo8IgaJdGYMR2ph", "eQ9Q36KPRyhz03VEJrDY"))
    }

    companion object {
        var user: UserData? = null
            set(value) {
                if (value != null) {
                    firestoreUserRef = firestoreUsersRef.document(value.userId)
                    firestoreUserPlantRef = firestoreUserRef!!.collection("plants")
                }
                field = value
            }

        var firestoreUserRef: DocumentReference? = null
        var firestoreUserPlantRef: CollectionReference? = null

        lateinit var firestoreUsersRef: CollectionReference
        lateinit var firestorePlantsRef: CollectionReference
        lateinit var firestoreGardensRef: CollectionReference
        lateinit var firestorePestsRef: CollectionReference
        lateinit var firestoreBenefitsRef: CollectionReference

        lateinit var storagePhotos: StorageReference

        lateinit var firebaseAuth: FirebaseAuth

        lateinit var languagePreference: LanguagePreference
    }

}