package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentPlantCardBinding
import com.entexy.gardenguru.ui.PlantMockData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardFragment : BaseFragment<FragmentPlantCardBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantData = PlantMockData.plant

        val idPlant = requireArguments().getString("PLANT_ID")

        with(binding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            title.text = plantData.name ?: plantData.variety

            calendar.setOnClickListener {
                findNavController().popBackStack(R.id.timetableFragment, false)
            }

            initPager(idPlant)
        }
    }

    private fun initPager(idPlant: String?) {
        with(binding) {
            val adapter = PlantCardPagerAdapter(requireActivity(), idPlant)
            viewPager.adapter = adapter

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            selectInfoTab()
                        }
                        1 -> {
                            selectNotificationTab()
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
            tvTabInfo.setTextColor(resources.getColor(R.color.primary_green, null))
            tvTabInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_green_dot)

            tvTabHistory.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)


            viewPager.setCurrentItem(0, true)
        }
    }

    private fun selectNotificationTab() {
        with(binding) {
            tvTabInfo.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabHistory.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            viewPager.setCurrentItem(1, true)
        }
    }

    private fun selectHistoryTab() {
        with(binding) {
            tvTabInfo.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabHistory.setTextColor(resources.getColor(R.color.primary_green, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_green_dot)

            viewPager.setCurrentItem(2, true)
        }
    }
}
