package com.entexy.gardenguru.ui.fragments.onboarding

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentOnboardingBinding
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
    }

    private fun setAdapter() = binding.apply {
        viewPager.adapter = PagerAdapter(this@OnboardingFragment)
        viewPager.offscreenPageLimit = 2
        dotsIndicator.attachTo(viewPager)
    }

    private fun setListeners() = binding.apply {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 2)
                    buttonNext.apply {
                        animate().alpha(1f).duration = 500L
                        setOnClickListener {
                            viewModel.changePref()
                            findNavController().navigate(R.id.loginFragment)
                        }
                    }
                else
                    buttonNext.apply {
                        animate().alpha(0f).duration = 500L
                        setOnClickListener(null)
                    }
            }
        })
        lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                onboarding.apply {
                    alpha = 0f
                    toVisible()
                    animate().alpha(1f).duration = 1000L
                }
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
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