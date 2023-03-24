package com.entexy.gardenguru.ui.fragments.add_plant.result

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentPlantSearchResultBinding
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo

class PlantSearchResultFragment(private val data: PlantData) : BaseFragment<FragmentPlantSearchResultBinding>(), GetPlantInfo {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(data)
    }

    private fun initView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext()).load(data.photo).fitCenter()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .transform(CenterCrop(), RoundedCorners(10)).into(plantPhoto)
            Glide.with(requireContext()).load(data.cover).circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder)).into(plantIcon)
            plantName.text = data.name
            careDifficult.initView(data.careComplexity, false)
            wheather.initView(data)
        }
    }

    override fun getPlantInfo(): PlantData {
        return data
    }
}