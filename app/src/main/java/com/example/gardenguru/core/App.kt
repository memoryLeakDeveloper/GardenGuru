package com.example.gardenguru.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInstance: App

    override fun onCreate() {
        super.onCreate()

    }

}