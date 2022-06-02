package com.example.gardenguru.ui.add_plant.description

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
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
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PlantDescriptionFragment(private val data: PlantData, private val clickCallback: AddingPlantFragment.ClickCallback) : Fragment() {

    private lateinit var binding: FragmentPlantDescriptionBinding
    private var isDescriptionShowed: Boolean = false

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
            plantInfo.movementMethod = LinkMovementMethod.getInstance()
            plantInfo.text = getSpannableNextString()
            careDifficult.initView(data.care_complexity, false)
            wheather.initView(data)
            careDescription.initView(data)
            pests.initView(data)
            benefits.initView(data)
        }
    }

    private fun showPlantDescription() {
        isDescriptionShowed = true
        binding.plantInfo.text = getSpannableHideString()
    }

    private fun hidePlantDescription() {
        isDescriptionShowed = false
        binding.plantInfo.text = getSpannableNextString()
    }

    private fun getSpannableNextString(): SpannableString {
        val text = data.description.substringBefore(".")
        val span = SpannableString(text + ". " + getString(R.string.next))
        span.setSpan(
            getClickableSpan(),
            0,
            text.length + getString(R.string.next).length + 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
            text.length + 2,
            text.length + getString(R.string.next).length + 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getSpannableHideString(): SpannableString {
        val text = data.description
        val span = SpannableString(text + " " + getString(R.string.hide))
        span.setSpan(
            getClickableSpan(),
            0,
            text.length + getString(R.string.hide).length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
            text.length + 1,
            text.length + getString(R.string.hide).length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getClickableSpan() = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            if (isDescriptionShowed) hidePlantDescription() else showPlantDescription()
            clickCallback.click()
        }
    }

}