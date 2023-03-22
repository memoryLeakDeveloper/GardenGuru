package com.entexy.gardenguru.ui.fragments.add_plant.description

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentPlantDescriptionBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDescriptionFragment(private val data: PlantData, private val callback: AddingPlantFragment.UpdateLayoutHeightCallback) :
    BaseFragment<FragmentPlantDescriptionBinding>(), GetPlantInfo {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundCompat(ContextCompat.getDrawable(requireContext(), R.drawable.primary_card_background))
        initView(data)
    }

    private fun initView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext()).load(data.photo).circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder)).into(plantPhoto)
            plantName.text = data.name
            plantName1.text = data.name
            data.description?.let {
                plantInfo.initView(it, callback)
            } ?: run {
                plantInfo.visibility = View.GONE
                aboutPlant.visibility = View.GONE
                plantName1.visibility = View.GONE
            }
            careDifficult.initView(data.careComplexity, false)
            wheather.initView(data)
            careDescription.initView(data)
            pests.initView(data.pests)
            benefits.initView(data.benefits)
        }
    }

    override fun getPlantInfo(): PlantData {
        return data
    }

}