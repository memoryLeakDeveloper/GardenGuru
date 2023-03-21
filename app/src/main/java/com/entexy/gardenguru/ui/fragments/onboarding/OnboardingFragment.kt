package com.entexy.gardenguru.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentOnboardingBinding
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setListeners()
        setVideoPlayer()
    }

    private fun setAdapters() = binding.apply {
        viewPager.adapter = PagerAdapter(this@OnboardingFragment)
        dotsIndicator.attachTo(viewPager)
    }

    private fun setListeners() = binding.apply {
        buttonNext.root.setOnClickListener {
            when (viewPager.currentItem) {
                0 -> viewPager.currentItem = 1
                1 -> viewPager.currentItem = 2
                2 -> {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
        buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun setVideoPlayer() = binding.apply {
        videoView.setVideoPath("android.resource://" + requireContext().packageName + "/" + R.raw.onboarding_video)
        videoView.setOnPreparedListener { player ->
            val screenWidth = resources.displayMetrics.widthPixels
            val videoWidth = player.videoWidth
            val videoHeight = player.videoHeight
            val videoProportion = videoWidth.toFloat() / videoHeight.toFloat()
            val newHeight = (screenWidth.toFloat() / videoProportion).toInt()
            videoView.layoutParams = videoView.layoutParams.apply {
                width = screenWidth
                height = newHeight
            }
        }
        videoView.start()
        videoView.setOnCompletionListener {
            onboarding.apply {
                alpha = 0f
                toVisible()
                animate().alpha(1f).duration = 1000L
            }
//            videoView.toGone()
        }
    }

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = 3

        override fun createFragment(position: Int) =
            when (position) {
                0 -> OnboardingStepFragment(0)
                1 -> OnboardingStepFragment(1)
                else -> OnboardingStepFragment(2)
            }
    }

}