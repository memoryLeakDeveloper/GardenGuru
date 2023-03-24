package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.databinding.CardBenefitsBinding
import com.entexy.gardenguru.utils.toGone

class BenefitsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardBenefitsBinding.inflate(LayoutInflater.from(context), this)

    fun initView(data: ArrayList<BenefitData>?) {
        if (data == null) {
            toGone()
        } else {
            if (data.isNotEmpty()) binding.benefits.text = data.first().name
        }
    }
}