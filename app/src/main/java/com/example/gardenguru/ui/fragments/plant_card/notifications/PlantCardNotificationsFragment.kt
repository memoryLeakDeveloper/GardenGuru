package com.example.gardenguru.ui.fragments.plant_card.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentPlantCardNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardNotificationsFragment : BaseFragment<FragmentPlantCardNotificationsBinding>() {

    private val viewModel: PlantCardNotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {


        }
    }
}
