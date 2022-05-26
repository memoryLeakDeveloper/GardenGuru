package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardCareDifficultBinding
import com.example.gardenguru.utils.Extensions.setDrawable
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDifficultCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: CardCareDifficultBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardCareDifficultBinding.inflate(inflater, this)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.edit.visibility = View.GONE
    }

    fun initView(difficult: Int, isEditing: Boolean) {
        when (difficult) {
            1 -> {
                binding.icon.setDrawable(R.drawable.ic_care_difficult_1)
                binding.textDescription.setText(R.string.care_difficult_1)
            }
            2 -> {
                binding.icon.setDrawable(R.drawable.ic_care_difficult_2)
                binding.textDescription.setText(R.string.care_difficult_2)
            }
            3 -> {
                binding.icon.setDrawable(R.drawable.ic_care_difficult_3)
                binding.textDescription.setText(R.string.care_difficult_3)
            }
            4 -> {
                binding.icon.setDrawable(R.drawable.ic_care_difficult_4)
                binding.textDescription.setText(R.string.care_difficult_4)
            }
        }

        if (isEditing)
            binding.edit.setOnClickListener {
                //TODO
            }
        else
            binding.edit.visibility = View.GONE
    }

}