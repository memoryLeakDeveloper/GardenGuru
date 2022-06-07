package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardBenefitsBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class BenefitsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardBenefitsBinding.inflate(LayoutInflater.from(context), this)

    init {
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        if (data.benefits?.isNotEmpty() == true) binding.benefits.text = data.benefits!!.first().type
    }
}