package com.example.gardenguru.ui.plantCard.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.example.gardenguru.databinding.FragmentPlantCardNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardNotificationsFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardNotificationsBinding
    private var viewModel = PlantCardNotificationsViewModel()//: PlantCardViewModel by viewModels()  todo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentPlantCardNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){


        }
    }
}
