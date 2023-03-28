package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.ui.fragments.plant_card.history.PlantCardHistoryFragment
import com.entexy.gardenguru.ui.fragments.plant_card.info.PlantCardInfoFragment

class PlantCardPagerAdapter(fa: FragmentActivity, private val plantData: PlantData) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PlantCardInfoFragment().apply {
                    arguments = Bundle().apply { putParcelable(PlantCardInfoFragment.CARD_INFO_PLANT_DATA_KEY, plantData) }
                }
            }
            else -> {
                PlantCardHistoryFragment().apply {
                    arguments = Bundle().apply { putString(PlantCardHistoryFragment.PLANT_HISTORY_CARD_PLANT_ID_EXTRA, plantData.id) }
                }
            }
        }
    }
}
