package com.example.gardenguru.ui.customview.card.pests

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardDiseasePestBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PestsCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: CardDiseasePestBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardDiseasePestBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        binding.recycler.apply {
            adapter = PestsCardAdapter(listOf(PestData("1", "EFKO", Uri.EMPTY), PestData("2", "QA", Uri.EMPTY), PestData("133", "YYYYY", Uri.EMPTY)))
            layoutManager = LinearLayoutManager(context)
        }
    }

}