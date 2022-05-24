package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.NoMatchesCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class NoMatchesCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: NoMatchesCardBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = NoMatchesCardBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }
}