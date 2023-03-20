package com.example.gardenguru.ui.fragments.info

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentPrivacyPolicyBinding
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