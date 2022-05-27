package com.example.gardenguru.ui.splash.screen

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentSplashScreenBinding
import com.example.gardenguru.utils.Extensions.getPrefs
import com.example.gardenguru.utils.PrefsKeys

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateNextPage()
    }

    private fun startAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 0F, 1F).apply {
            startDelay = 500
            duration = 1000
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    navigateNextPage()
                }
            })
        }.start()
    }

    private fun navigateNextPage() {
        val firstLaunch = requireContext().getPrefs().getBoolean(PrefsKeys.FIRST_APP_LAUNCH, true)
        if (firstLaunch){
            requireContext().getPrefs().edit().putBoolean(PrefsKeys.FIRST_APP_LAUNCH, false).apply()
            findNavController().navigate(R.id.onboardingFragment)
        }else{
            findNavController().navigate(R.id.loginFragment)
        }
    }

}