package com.example.gardenguru.ui.customview.card.weather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardWheatherConditionBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class WheatherConditionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: CardWheatherConditionBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardWheatherConditionBinding.inflate(inflater, this)
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        //TODO
    }

}