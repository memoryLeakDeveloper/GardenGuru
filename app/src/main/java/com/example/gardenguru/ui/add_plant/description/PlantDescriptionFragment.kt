package com.example.gardenguru.ui.add_plant.description

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.FragmentPlantDescriptionBinding
import com.example.gardenguru.ui.add_plant.AddingPlantFragment
import com.example.gardenguru.ui.add_plant.GetPlantInfo
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDescriptionFragment(private val data: PlantData, private val callback: AddingPlantFragment.UpdateLayoutHeightCallback) :
    Fragment(), GetPlantInfo {

    private lateinit var binding: FragmentPlantDescriptionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlantDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundCompat(ContextCompat.getDrawable(requireContext(), R.drawable.primary_card_background))
        initView(data)
    }

    private fun initView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo.file)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name
            plantName1.text = data.name
            data.description?.let {
                plantInfo.initView(it, callback)
            } ?: run {
                plantInfo.visibility = View.GONE
                aboutPlant.visibility = View.GONE
                plantName1.visibility = View.GONE
            }
            careDifficult.initView(data.careComplexity!!, false)
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