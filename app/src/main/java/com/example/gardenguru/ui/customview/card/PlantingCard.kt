package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardPlantingBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PlantingCard(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardPlantingBinding.inflate(LayoutInflater.from(context), this)
    private var valueCallback: ValueCallback? = null

    fun interface ValueCallback {
        fun value(text: String)
    }

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.button.setOnClickListener {
            checkValidInput()
        }
    }

    private fun checkValidInput() {
        with(binding) {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                disableEditText(editText)
                valueCallback?.value((text))
                setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
                button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_unactive_background))
                button.isEnabled = false
            } else {
                setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
            }
        }
    }

    private fun disableEditText(editText: EditText) {
        editText.isEnabled = false
        editText.isActivated = false
        editText.isClickable = false
    }
}