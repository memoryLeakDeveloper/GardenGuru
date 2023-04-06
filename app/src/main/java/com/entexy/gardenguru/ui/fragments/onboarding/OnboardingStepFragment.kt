package com.entexy.gardenguru.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentOnboardingStepBinding
import com.entexy.gardenguru.utils.setString

class OnboardingStepFragment() : BaseFragment<FragmentOnboardingStepBinding>() {

    private var position = 0

    constructor(position: Int) : this() {
        this.position = position
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.apply {
        when (position) {
            0 -> {
                setBackground(R.drawable.onboarding_1)
                textHead.setString(R.string.onboarding1_head)
                textHint.setString(R.string.onboarding1_hint)
            }
            1 -> {
                setBackground(R.drawable.onboarding_2)
                textHead.setString(R.string.onboarding2_head)
                textHint.setString(R.string.onboarding2_hint)
            }
            2 -> {
                setBackground(R.drawable.onboarding_3)
                textHead.setString(R.string.onboarding3_head)
                textHint.setString(R.string.onboarding3_hint)
            }
        }
    }

    private fun setBackground(@DrawableRes drawable: Int) {
        Glide.with(requireContext()).load(drawable).centerCrop().into(binding.ivBack)
    }

}