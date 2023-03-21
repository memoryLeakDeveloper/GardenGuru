package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.CardPlantingBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class PlantingCard(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardPlantingBinding.inflate(LayoutInflater.from(context), this)
    private var isActive = true
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
            if (isActive) {
                checkValidInput()
            } else {
                setViewActive()
                isActive = true
            }
        }
    }

    private fun checkValidInput() {
        if (binding.editText.text.toString().isNotEmpty()) {
            setViewInactive()
            isActive = false
        } else {
            setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
        }
    }

    private fun setViewActive() {
        with(binding) {
            disableEditText(true)
            editText.text?.clear()
            valueCallback?.value("")
            button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_background))
            button.setText(R.string.Ok)
            button.isEnabled = true
        }
    }

    private fun setViewInactive() {
        with(binding) {
            disableEditText(false)
            valueCallback?.value(editText.text.toString())
            setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
            button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_unactive_background))
            button.setText(R.string.change)
            button.isEnabled = false
        }
    }

    private fun disableEditText(isEnabled: Boolean) {
        binding.editText.isEnabled = isEnabled
        binding.editText.isActivated = isEnabled
        binding.editText.isClickable = isEnabled
    }
}