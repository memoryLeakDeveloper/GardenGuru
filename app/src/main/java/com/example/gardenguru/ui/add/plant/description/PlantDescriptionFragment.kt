package com.example.gardenguru.ui.add.plant.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.databinding.PlantDescriptionFragmentBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PlantDescriptionFragment() : Fragment() {

    private lateinit var binding: PlantDescriptionFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PlantDescriptionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundCompat(ContextCompat.getDrawable(requireContext(), R.drawable.primary_card_background))
    }

}