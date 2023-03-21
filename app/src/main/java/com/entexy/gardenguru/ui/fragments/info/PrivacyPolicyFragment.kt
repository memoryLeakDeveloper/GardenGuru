package com.entexy.gardenguru.ui.fragments.info

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentPrivacyPolicyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : BaseFragment<FragmentPrivacyPolicyBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.privacy_policy)
        binding.header.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        binding.header.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}