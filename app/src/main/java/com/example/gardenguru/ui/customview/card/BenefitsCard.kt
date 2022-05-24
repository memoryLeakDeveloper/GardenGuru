package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.BenefitsCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class BenefitsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: BenefitsCardBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BenefitsCardBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        binding.benefits.text = data.benefits.first().type
    }
}