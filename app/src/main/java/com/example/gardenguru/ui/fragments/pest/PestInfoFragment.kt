package com.example.gardenguru.ui.fragments.pest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentPestInfoBinding
import com.example.gardenguru.utils.Extensions.setDrawable

class PestInfoFragment : Fragment() {

    private lateinit var binding: FragmentPestInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPestInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

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