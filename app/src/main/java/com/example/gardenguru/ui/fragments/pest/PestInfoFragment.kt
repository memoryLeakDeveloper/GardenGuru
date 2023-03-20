package com.example.gardenguru.ui.fragments.pest

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentPestInfoBinding
import com.example.gardenguru.utils.Extensions.setDrawable

class PestInfoFragment : BaseFragment<FragmentPestInfoBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            header.title.setText(R.string.disease_and_pests)
            header.menu.apply {
                visibility = View.VISIBLE
                setDrawable(R.drawable.ic_calendar)
            }
            header.back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}