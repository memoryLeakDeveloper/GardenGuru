package com.example.gardenguru.ui.customview.card.pests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.databinding.CardDiseasePestBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PestsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardDiseasePestBinding.inflate(LayoutInflater.from(context), this)

    init {
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: ArrayList<PestData>?) {
        if (data == null) {
            this.visibility = View.GONE
        } else {
            binding.recycler.apply {
                adapter = PestsCardAdapter(data)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}