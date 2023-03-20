package com.example.gardenguru.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentOnboardingStepBinding
import com.example.gardenguru.utils.setDrawable

class OnboardingStepFragment(private val position: Int) : BaseFragment<FragmentOnboardingStepBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        when (position) {
            0 -> {
                binding.imageView.setDrawable(R.drawable.onboarding1_image)
                binding.textHead.text = getString(R.string.onboarding1_head)
                binding.textHint.text = getString(R.string.onboarding1_hint)
            }
            1 -> {
                binding.imageView.setDrawable(R.drawable.onboarding2_image)
                binding.textHead.text = getString(R.string.onboarding2_head)
                binding.textHint.text = getString(R.string.onboarding2_hint)
            }
            2 -> {
                binding.imageView.setDrawable(R.drawable.onboarding3_image)
                binding.textHead.text = getString(R.string.onboarding3_head)
                binding.textHint.text = getString(R.string.onboarding3_hint)
            }
        }
    }
}