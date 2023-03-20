package com.entexy.gardenguru.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setListeners()
    }

    private fun setAdapters() {
        binding.viewPager.adapter = PagerAdapter(this)
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun setListeners() {
        binding.buttonNext.root.setOnClickListener {
            when (binding.viewPager.currentItem) {
                0 -> binding.viewPager.currentItem = 1
                1 -> binding.viewPager.currentItem = 2
                2 -> {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
        binding.buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = 3

        override fun createFragment(position: Int) =
            when (position) {
                0 -> OnboardingStepFragment(0)
                1 -> OnboardingStepFragment(1)
                2 -> OnboardingStepFragment(2)
                else -> OnboardingStepFragment(0)
            }
    }

}