package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.databinding.FragmentPlantCardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class PlantCardFragment : BaseFragment<FragmentPlantCardBinding>() {

    private val viewModel: PlantCardViewModel by viewModels()

    private lateinit var adapter: PlantCardPagerAdapter

    private lateinit var plantData: PlantData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        plantData = PlantData(
            "wqqweqwe",
            "НЕЗАБУДКА",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            null,
            null,
            arrayListOf(BenefitData("qweqweqweqweqwe", "qwpoqfwepofqmvw")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            Date(),
            3,
            4,
            5,
            6,
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
