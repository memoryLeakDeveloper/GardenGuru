package com.entexy.gardenguru.ui.fragments.plant_card.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.FragmentPlantCardHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardHistoryFragment : BaseFragment<FragmentPlantCardHistoryBinding>() {

    private var historyAdapter: HistoryRecyclerAdapter? = null
    private val viewModel: PlantCardHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events = requireArguments().getParcelableArrayList<EventData>(PLANT_HISTORY_CARD_EVENTS_EXTRA)!!
        val plantData = requireArguments().getParcelable<PlantData>(PLANT_HISTORY_CARD_PLANT_EXTRA)!!

        val predictedEvents = viewModel.predictEvents(plantData, events)

        historyAdapter = HistoryRecyclerAdapter(ArrayList(predictedEvents))
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = historyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        historyAdapter = null
    }

    companion object {
        const val PLANT_HISTORY_CARD_EVENTS_EXTRA = "plant-id-extra"
        const val PLANT_HISTORY_CARD_PLANT_EXTRA = "plant-extra"
    }
}
