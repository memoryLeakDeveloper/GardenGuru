package com.entexy.gardenguru.ui.customview.card.pests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.databinding.CardDiseasePestBinding
import com.entexy.gardenguru.utils.toGone

class PestsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardDiseasePestBinding.inflate(LayoutInflater.from(context), this)

    fun initView(data: ArrayList<PestData>?) {
        if (data == null || data.isEmpty()) {
            toGone()
            binding.divider.toGone()
        } else {
            binding.recycler.apply {
                adapter = PestsCardAdapter(data)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}