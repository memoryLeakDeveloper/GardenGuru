package com.example.gardenguru.ui.plantCard.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.databinding.FragmentPlantCardInfoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardInfoBinding
    private var viewModel = PlantCardInfoViewModel()//: PlantCardViewModel by viewModels()  todo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentPlantCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){


        }
    }
}
