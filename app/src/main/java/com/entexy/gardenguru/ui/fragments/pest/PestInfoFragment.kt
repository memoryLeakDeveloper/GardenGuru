package com.entexy.gardenguru.ui.fragments.pest

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentPestInfoBinding
import com.entexy.gardenguru.utils.setDrawable
import com.entexy.gardenguru.utils.toVisible

class PestInfoFragment : BaseFragment<FragmentPestInfoBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            header.title.setText(R.string.disease_and_pests)
            header.menu.apply {
                toVisible()
                setDrawable(R.drawable.ic_calendar)
            }
            header.back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}