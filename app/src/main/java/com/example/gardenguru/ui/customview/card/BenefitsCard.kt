package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.databinding.CardBenefitsBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class BenefitsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardBenefitsBinding.inflate(LayoutInflater.from(context), this)

    init {
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: ArrayList<BenefitData>?) {
        if (data == null) {
            this.visibility = View.GONE
        } else {
            if (data.isNotEmpty()) binding.benefits.text = data.first().type
        }
    }
}