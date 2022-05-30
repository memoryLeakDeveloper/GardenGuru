package com.example.gardenguru.ui.add_plant.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentClientPlantBinding
import com.example.gardenguru.data.enums.Seasons
import com.example.gardenguru.ui.add_plant.AddingPlantFragment

class ClientPlantFragment(private val clickCallback: AddingPlantFragment.ClickCallback) : Fragment() {

    lateinit var binding: FragmentClientPlantBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentClientPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowDown.setOnClickListener {
            binding.shortForm.visibility = View.GONE
            binding.fullForm.visibility = View.VISIBLE
            clickCallback.click()
        }
        binding.arrowUp.setOnClickListener {
            binding.shortForm.visibility = View.VISIBLE
            binding.fullForm.visibility = View.GONE
            clickCallback.click()
        }
        val listCareDifficult = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_difficult)))
        binding.spinnerCare.initView(getString(R.string.define_care_difficult), null, listCareDifficult, false)
        val listCareType = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_type)))
        binding.spinnerWatering.initView(getString(R.string.watering), null, listCareType, false)
        binding.spinnerPests.initView(getString(R.string.choose_pests), null, arrayListOf("EFKO", "NATASHA", "COCA-COLA", "333333"), false)
        binding.calendarWinter.initView(Seasons.Winter)
        binding.calendarSummer.initView(Seasons.Summer)
//        binding.spinner.initView("ssdfdsfsd", null , arrayListOf("11111", "22222222", "333333333", "44444444444"))
    }
}