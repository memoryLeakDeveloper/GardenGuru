package com.entexy.gardenguru.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentOnboardingStepBinding
import com.entexy.gardenguru.utils.setDrawable
import com.entexy.gardenguru.utils.setString

class OnboardingStepFragment(private val position: Int) : BaseFragment<FragmentOnboardingStepBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.apply {
        when (position) {
            0 -> {
                imageView.setDrawable(R.drawable.onboarding1_image)
                textHead.setString(R.string.onboarding1_head)
                textHint.setString(R.string.onboarding1_hint)
            }
            1 -> {
                imageView.setDrawable(R.drawable.onboarding2_image)
                textHead.setString(R.string.onboarding2_head)
                textHint.setString(R.string.onboarding2_hint)
            }
            2 -> {
                imageView.setDrawable(R.drawable.onboarding3_image)
                textHead.setString(R.string.onboarding3_head)
                textHint.setString(R.string.onboarding3_hint)
            }
        }
    }
}