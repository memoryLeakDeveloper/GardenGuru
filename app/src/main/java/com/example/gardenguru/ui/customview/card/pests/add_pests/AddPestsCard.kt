package com.example.gardenguru.ui.customview.card.pests.add_pests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.AddPestsCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class AddPestsCard @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attributeSet, defStyleAttr) {

    private lateinit var binding: AddPestsCardBinding

    init {
//        val inflater = LayoutInflater.from(context)
//        binding = AddPestsCardBinding.inflate(inflater, this)
//        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.spinner_background))
    }
}