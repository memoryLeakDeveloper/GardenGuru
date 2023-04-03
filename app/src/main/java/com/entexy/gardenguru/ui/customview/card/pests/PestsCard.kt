package com.entexy.gardenguru.ui.customview.card.pests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.databinding.CardDiseasePestBinding
import com.entexy.gardenguru.utils.toGone

class PestsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardDiseasePestBinding.inflate(LayoutInflater.from(context), this)

    fun initView(data: List<PestData>?) = binding.apply {
        if (data == null || data.isEmpty()) {
            toGone()
        } else {
            recycler.adapter = PestsCardAdapter(data)
            recycler.layoutManager = LinearLayoutManager(context)
        }
    }
}