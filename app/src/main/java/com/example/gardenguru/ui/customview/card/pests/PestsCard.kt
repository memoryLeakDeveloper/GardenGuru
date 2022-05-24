package com.example.gardenguru.ui.customview.card.pests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.DiseasePestCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PestsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: DiseasePestCardBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DiseasePestCardBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        binding.recycler.apply {
            adapter = PestsCardAdapter(listOf(PestData("1", "EFKO"), PestData("2", "QA"), PestData("133", "YYYYY")))
            layoutManager = LinearLayoutManager(context)
        }
    }

}