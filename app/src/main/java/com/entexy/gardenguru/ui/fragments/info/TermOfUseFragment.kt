package com.entexy.gardenguru.ui.fragments.info

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentTermOfUseBinding
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