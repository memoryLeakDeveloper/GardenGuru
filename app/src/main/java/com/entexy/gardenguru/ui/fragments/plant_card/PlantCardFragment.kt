package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentPlantCardBinding
import com.entexy.gardenguru.ui.PlantMockData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantCardFragment : BaseFragment<FragmentPlantCardBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val plantData = requireArguments().getParcelable<PlantData>(PLANT_CARD_PLANT_DATA_KEY)
        val plantData = PlantMockData.plant
        initView(plantData)

        with(binding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            calendar.setOnClickListener {
                findNavController().popBackStack(R.id.timetableFragment, false)
            }
        }
    }

    private fun initView(plantData: PlantData) {

        binding.title.text = plantData.customName ?: plantData.variety
        initPager(plantData)
    }

    private fun initPager(plantData: PlantData) {
        with(binding) {
            val adapter = PlantCardPagerAdapter(requireActivity(), plantData)
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
