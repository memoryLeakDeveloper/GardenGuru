package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.FragmentPlantCardBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class PlantCardFragment : BaseFragment<FragmentPlantCardBinding>() {

    private val viewModel: PlantCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantData = requireArguments().getParcelable<PlantData>(PLANT_CARD_PLANT_DATA_KEY)!!

        lifecycleScope.launch {

            val dialogHelper = DialogHelper()

            viewModel.fetchEvents(plantData.id).collect { cloudResponse ->
                cloudResponse.getResult(
                    success = {
                        dialogHelper.hideDialog()

                        initView(plantData, ArrayList(it.result.sortedByDescending { it.eventTime.time }).apply {
                            add(0, EventData("", true, Calendar.getInstance().apply {
                                time = plantData.addingTime
                            }, EventData.EventType.Create, plantData.id))
                        })
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

        with(binding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            calendar.setOnClickListener {
                findNavController().popBackStack(R.id.timetableFragment, false)
            }
        }
    }

    private fun initView(plantData: PlantData, events: ArrayList<EventData>) {

        binding.title.text = plantData.getPlantName("ru")
        initPager(plantData, events)
    }

    private fun initPager(plantData: PlantData, events: ArrayList<EventData>) {
        with(binding) {
            val adapter = PlantCardPagerAdapter(requireActivity(), plantData, events) {
                binding.title.text = it
            }
            viewPager.adapter = adapter

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            selectInfoTab()
                        }
                        else -> {
                            selectHistoryTab()
                        }
                    }
                }
            })

            tvTabInfo.setOnClickListener {
                selectInfoTab()
            }

            tvTabHistory.setOnClickListener {
                selectHistoryTab()
            }
        }
    }

    private fun selectInfoTab() {
        with(binding) {
            tvTabInfo.setBackgroundResource(R.drawable.bg_active_segment_picker)
            tvTabHistory.background = null

            viewPager.setCurrentItem(0, true)
        }
    }

    private fun selectHistoryTab() {
        with(binding) {
            tvTabHistory.setBackgroundResource(R.drawable.bg_active_segment_picker)
            tvTabInfo.background = null

            viewPager.setCurrentItem(2, true)
        }
    }

    companion object {
        const val PLANT_CARD_PLANT_DATA_KEY = "plant-card-plant-data"
    }
}
