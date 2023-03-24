package com.entexy.gardenguru.ui.fragments.add_plant.result

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentNoMatchesSearchBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment

class NoMatchesSearchFragment : BaseFragment<FragmentNoMatchesSearchBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.apply {
        imagePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_addingPlantFragment_to_cameraFragment)
        }
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findNavController().navigate(
                    R.id.addingPlantFragment,
                    bundleOf(AddingPlantFragment.SEARCH_BY_VARIETY_ARGUMENTS_KEY to editText.text.toString())
                )
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }


    }


}