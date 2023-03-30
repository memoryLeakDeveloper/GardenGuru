package com.entexy.gardenguru.ui.fragments.plant_card.history

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.FragmentPlantCardHistoryBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class PlantCardHistoryFragment : BaseFragment<FragmentPlantCardHistoryBinding>() {

    private var historyAdapter: HistoryRecyclerAdapter? = null
    private val viewModel: PlantCardHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantId = requireArguments().getString(PLANT_HISTORY_CARD_PLANT_ID_EXTRA)!!
        val plantingTime = Calendar.getInstance().apply {
            timeInMillis = requireArguments().getLong(PLANT_HISTORY_CARD_PLANTING_TIME_EXTRA)
        }
        lifecycleScope.launch {

            val dialogHelper = DialogHelper()

            viewModel.fetchEvents(plantId).collect { cloudResponse ->
                cloudResponse.getResult(
                    success = {
                        with(binding) {
                            dialogHelper.hideDialog()
                            historyAdapter = HistoryRecyclerAdapter(ArrayList(it.result.sortedByDescending { it.eventTime.time }).apply {
                                add(0, EventData("", true, plantingTime, EventData.EventType.Create))
                            })
                            rvHistory.layoutManager = LinearLayoutManager(requireContext())
                            rvHistory.adapter = historyAdapter
                        }
                    },
                    failure = {
                        dialogHelper.hideDialog()
                        requireView().showSnackBar(R.string.error_loading_data)
                    },
                    loading = {
                        dialogHelper.showDialog(ProgressBar(requireContext()), cancelable = false)
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        historyAdapter = null
    }

    companion object {
        const val PLANT_HISTORY_CARD_PLANT_ID_EXTRA = "plant-id-extra"
        const val PLANT_HISTORY_CARD_PLANTING_TIME_EXTRA = "planting_time-extra"
    }
}
