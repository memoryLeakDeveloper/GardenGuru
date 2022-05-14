package com.example.gardenguru.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.fragment_container)
        navController.navigate(R.id.splashScreenFragment)

        setInsets()
    }

    private fun setInsets() {
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        }
    }

    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.splashScreenFragment -> {}
            else -> {
                super.onBackPressed()
            }
        }
    }
}