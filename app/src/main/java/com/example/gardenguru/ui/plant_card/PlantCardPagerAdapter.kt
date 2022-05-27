package com.example.gardenguru.ui.plant_card

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gardenguru.ui.plant_card.history.PlantCardHistoryFragment
import com.example.gardenguru.ui.plant_card.info.PlantCardInfoFragment
import com.example.gardenguru.ui.plant_card.notifications.PlantCardNotificationsFragment

class PlantCardPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PlantCardInfoFragment()
            }
            1 -> {
                PlantCardNotificationsFragment()
            }
            else -> {
                PlantCardHistoryFragment()
            }
        }
    }
}
