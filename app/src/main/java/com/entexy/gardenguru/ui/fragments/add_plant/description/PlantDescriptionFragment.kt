package com.entexy.gardenguru.ui.fragments.add_plant.description

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentPlantDescriptionBinding
import com.entexy.gardenguru.utils.setString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDescriptionFragment : BaseFragment<FragmentPlantDescriptionBinding>() {

    private var data: PlantData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelable(PLANT_DATA_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data?.let { initView(it) } ?: run { requireActivity().onBackPressed() }
    }

    private fun initView(data: PlantData) = binding.apply {
        header.title.setString(R.string.adding)
        header.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        Glide.with(requireContext()).load(data.photo).fitCenter()
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
            .transform(CenterCrop(), RoundedCorners(10)).into(plantPhoto)
        Glide.with(requireContext()).load(data.coverPhoto).circleCrop()
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder)).into(plantIcon)
        plantName.text = data.name
        plantInfo.initView(data.description)
        careDifficult.initView(data.careComplexity, false)
        wheather.initView(data)
        careDescription.initView(data)
        pests.initView(data.pests)
        benefits.initView(data.benefits)
    }

    companion object {
        const val PLANT_DATA_KEY = "PLANT_DATA_KEY"
    }

}