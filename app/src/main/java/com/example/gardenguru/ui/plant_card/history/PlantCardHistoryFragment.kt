package com.example.gardenguru.ui.plant_card.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.databinding.FragmentPlantCardHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardHistoryFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardHistoryBinding
    private var viewModel = PlantCardHistoryViewModel()//: PlantCardViewModel by viewModels()  todo

    private val historyAdapter = HistoryRecyclerAdapter(viewModel)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentPlantCardHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            rvHistory.adapter = historyAdapter

        }
    }
}
