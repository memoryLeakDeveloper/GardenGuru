package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.ui.fragments.plant_card.history.PlantCardHistoryFragment
import com.entexy.gardenguru.ui.fragments.plant_card.info.PlantCardInfoFragment

class PlantCardPagerAdapter(fa: FragmentActivity, private val plantData: PlantData, private val events: ArrayList<EventData>, private val nameUpdateCallback: (String) -> Unit) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PlantCardInfoFragment().apply {
                    arguments = Bundle().apply {
                        setUpdateNameCallback(nameUpdateCallback)
                        putParcelable(PlantCardInfoFragment.CARD_INFO_PLANT_DATA_KEY, plantData)
                        putParcelableArrayList(PlantCardInfoFragment.CARD_INFO_PLANT_EVENTS_KEY, events)
                    }
                }
            }
            else -> {
                PlantCardHistoryFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(PlantCardHistoryFragment.PLANT_HISTORY_CARD_PLANT_EXTRA, plantData)
                        putParcelableArrayList(PlantCardHistoryFragment.PLANT_HISTORY_CARD_EVENTS_EXTRA, events)
                    }
                }
            }
        }
    }
}
