package com.entexy.gardenguru.ui.fragments.onboarding

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.prefs.FirstLaunchPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val firstLaunchPref: FirstLaunchPref): ViewModel() {

    fun changePref() = firstLaunchPref.put(false)
}