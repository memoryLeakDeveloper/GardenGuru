package com.entexy.gardenguru.ui.fragments.plant_card.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentPlantCardHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardHistoryFragment : BaseFragment<FragmentPlantCardHistoryBinding>() {

    private val viewModel: PlantCardHistoryViewModel by viewModels()

    private var historyAdapter: HistoryRecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            historyAdapter = HistoryRecyclerAdapter(viewModel)
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            rvHistory.adapter = historyAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        historyAdapter = null
    }
}
