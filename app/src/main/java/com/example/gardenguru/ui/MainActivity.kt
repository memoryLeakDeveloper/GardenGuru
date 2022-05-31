package com.example.gardenguru.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()
        navController = Navigation.findNavController(this, R.id.fragment_container)
    }

    private fun setInsets() {
        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
                )
            )
        }
    }



    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.onboardingFragment, R.id.timetableFragment, R.id.loginFragment -> {
                super.onBackPressed()
            }
            else -> {
                navController.popBackStack()
            }
        }
    }
}