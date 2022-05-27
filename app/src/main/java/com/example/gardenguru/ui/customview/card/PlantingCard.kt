package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardPlantingBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PlantingCard(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private lateinit var binding: CardPlantingBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardPlantingBinding.inflate(inflater, this)
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }
}