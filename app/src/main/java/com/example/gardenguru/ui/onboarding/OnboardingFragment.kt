package com.example.gardenguru.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gardenguru.R
import com.example.gardenguru.databinding.OnboardingFragmentBinding

class OnboardingFragment: Fragment() {

    private lateinit var binding: OnboardingFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = OnboardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PagerAdapter(this)
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.buttonNext.root.setOnClickListener {
            when(binding.viewPager.currentItem) {
                0 -> binding.viewPager.currentItem = 1
                1 -> binding.viewPager.currentItem = 2
                2 -> {
                    //TODO
                }
            }
        }
        binding.buttonSkip.setOnClickListener {
            //TODO
//            findNavController().navigate()
        }
    }

    private inner class PagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

        override fun getItemCount() = 3

        override fun createFragment(position: Int) =
            when(position) {
                0 ->  OnboardingStepFragment(0)
                1 ->  OnboardingStepFragment(1)
                2 ->  OnboardingStepFragment(2)
                else -> OnboardingStepFragment(0)
        }
    }



}