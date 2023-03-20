package com.example.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.FragmentPlantCardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardFragment : BaseFragment<FragmentPlantCardBinding>() {

    private val viewModel: PlantCardViewModel by viewModels()

    private lateinit var adapter: PlantCardPagerAdapter

    private lateinit var plantData: PlantData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plantData = PlantData(
            "0",
            0,
            "Иван",
            null,
            "Кактус",
            (
                    PhotoData(
                        "0",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    )
                    ),
            SunRelationData(0, ""),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        )

        val idPlant = requireArguments().getString("PLANT_ID")

        with(binding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            title.text = plantData.name

            calendar.setOnClickListener {
                findNavController().popBackStack(R.id.timetableFragment, false)
            }

            initPager(idPlant)
        }
    }

    private fun initPager(idPlant: String?) {
        with(binding) {
            adapter = PlantCardPagerAdapter(requireActivity(), idPlant)
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

            tvTabNotifications.setOnClickListener {
                selectNotificationTab()
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

            tvTabNotifications.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabNotifications.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabHistory.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)


            viewPager.setCurrentItem(0, true)
        }
    }

    private fun selectNotificationTab() {
        with(binding) {
            tvTabInfo.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabNotifications.setTextColor(resources.getColor(R.color.primary_green, requireContext().theme))
            tvTabNotifications.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_green_dot)

            tvTabHistory.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            viewPager.setCurrentItem(1, true)
        }
    }

    private fun selectHistoryTab() {
        with(binding) {
            tvTabInfo.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabNotifications.setTextColor(resources.getColor(R.color.gray, requireContext().theme))
            tvTabNotifications.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            tvTabHistory.setTextColor(resources.getColor(R.color.primary_green, requireContext().theme))
            tvTabHistory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_green_dot)

            viewPager.setCurrentItem(2, true)
        }
    }
}
