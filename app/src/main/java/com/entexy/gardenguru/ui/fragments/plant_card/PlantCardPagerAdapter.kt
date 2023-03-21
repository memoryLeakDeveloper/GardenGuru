package com.entexy.gardenguru.ui.fragments.plant_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.ui.fragments.plant_card.history.PlantCardHistoryFragment
import com.entexy.gardenguru.ui.fragments.plant_card.info.PlantCardInfoFragment
import com.entexy.gardenguru.ui.fragments.plant_card.notifications.PlantCardNotificationsFragment

class PlantCardPagerAdapter(fa: FragmentActivity, private val idPlant: String?) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PlantCardInfoFragment().apply { arguments = Bundle().apply { putString("PLANT_ID", idPlant) } }
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