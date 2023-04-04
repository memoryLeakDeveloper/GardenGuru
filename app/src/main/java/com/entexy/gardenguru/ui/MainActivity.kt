package com.entexy.gardenguru.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.prefs.FirstLaunchPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firstLaunchPref: FirstLaunchPref
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
        if (firstLaunchPref.get()) {
            navController?.navigate(R.id.onboardingFragment)
        }
    }

    override fun onBackPressed() {
        when (navController?.currentDestination?.id) {
            R.id.onboardingFragment, R.id.timetableFragment, R.id.loginFragment -> {
                super.onBackPressed()
            }
            else -> {
                navController?.popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController = null
    }
}