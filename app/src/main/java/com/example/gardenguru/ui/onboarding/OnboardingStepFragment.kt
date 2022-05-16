package com.example.gardenguru.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.databinding.OnboardingStepFragmentBinding
import com.example.gardenguru.utils.Extensions.setDrawable

class OnboardingStepFragment(private val position: Int) : Fragment() {

    private lateinit var binding: OnboardingStepFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = OnboardingStepFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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