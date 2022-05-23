package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.DiseasePestCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class DiseasePestCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: DiseasePestCardBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DiseasePestCardBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

}