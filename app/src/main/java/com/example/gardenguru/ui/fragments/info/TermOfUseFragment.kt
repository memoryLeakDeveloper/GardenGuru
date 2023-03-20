package com.example.gardenguru.ui.fragments.info

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentTermOfUseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermOfUseFragment : BaseFragment<FragmentTermOfUseBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.terms_of_use)
        binding.header.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}