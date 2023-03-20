package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.CardNoMatchesBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class NoMatchesCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: CardNoMatchesBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardNoMatchesBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }
}