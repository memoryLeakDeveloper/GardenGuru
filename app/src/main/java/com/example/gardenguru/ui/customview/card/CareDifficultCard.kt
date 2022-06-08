package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardCareDifficultBinding
import com.example.gardenguru.databinding.DialogCareDifficultBinding
import com.example.gardenguru.ui.customview.DialogHelper
import com.example.gardenguru.utils.Extensions.setDrawable
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDifficultCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = CardCareDifficultBinding.inflate(LayoutInflater.from(context), this)
    private val bindingDialog = DialogCareDifficultBinding.inflate(LayoutInflater.from(context))
    private val dialog = DialogHelper()
    private var careDifficult = 0

    init {
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(difficult: Int?, isEditing: Boolean) {
        this.careDifficult = difficult ?: 1
        with(binding) {
            if (!isEditing) edit.visibility = View.GONE
            else edit.setOnClickListener {
                showDialog()
            }
            when (difficult) {
                1 -> {
                    icon.setDrawable(R.drawable.ic_care_difficult_1)
                    textDescription.setText(R.string.care_difficult_1)
                }
                2 -> {
                    icon.setDrawable(R.drawable.ic_care_difficult_2)
                    textDescription.setText(R.string.care_difficult_2)
                }
                3 -> {
                    icon.setDrawable(R.drawable.ic_care_difficult_3)
                    textDescription.setText(R.string.care_difficult_3)
                }
                4 -> {
                    icon.setDrawable(R.drawable.ic_care_difficult_4)
                    textDescription.setText(R.string.care_difficult_4)
                }
            }
        }
    }

    fun getDifficult() = this.careDifficult

    private fun showDialog() {
        var selectedItem = 0
        val listCare = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_difficult)))
        with(bindingDialog) {
            card1.apply {
                val position = 1
                text1.text = listCare[position - 1]
                image1.setDrawable(R.drawable.ic_care_difficult_1)
                setOnClickListener {
                    if (selectedItem == position) return@setOnClickListener
                    setItemSelected(position)
                    setItemUnselected(selectedItem)
                    selectedItem = position
                }
            }
            card2.apply {
                val position = 2
                text2.text = listCare[position - 1]
                image2.setDrawable(R.drawable.ic_care_difficult_2)
                setOnClickListener {
                    if (selectedItem == position) return@setOnClickListener
                    setItemSelected(position)
                    setItemUnselected(selectedItem)
                    selectedItem = position
                }
            }
            card3.apply {
                val position = 3
                text3.text = listCare[position - 1]
                image3.setDrawable(R.drawable.ic_care_difficult_3)
                setOnClickListener {
                    if (selectedItem == position) return@setOnClickListener
                    setItemSelected(position)
                    setItemUnselected(selectedItem)
                    selectedItem = position
                }
            }
            card4.apply {
                val position = 4
                text4.text = listCare[position - 1]
                image4.setDrawable(R.drawable.ic_care_difficult_4)
                setOnClickListener {
                    if (selectedItem == position) return@setOnClickListener
                    setItemSelected(position)
                    setItemUnselected(selectedItem)
                    selectedItem = position
                }
            }
        }
        dialog.showDialog(bindingDialog.root, false)
    }

    private fun setItemSelected(position: Int) {
        with(bindingDialog) {
            when (position) {
                1 -> {
                    card1.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.selected_card_background))
                    text1.setTextColor(ContextCompat.getColor(context, R.color.gray3))
                }
                2 -> {
                    card2.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.selected_card_background))
                    text2.setTextColor(ContextCompat.getColor(context, R.color.gray3))
                }
                3 -> {
                    card3.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.selected_card_background))
                    text3.setTextColor(ContextCompat.getColor(context, R.color.gray3))
                }
                4 -> {
                    card4.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.selected_card_background))
                    text4.setTextColor(ContextCompat.getColor(context, R.color.gray3))
                }
            }
            button.apply {
                setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_background))
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next_arrow, 0)
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setOnClickListener {
                    dialog.hideDialog()
                    initView(position, true)
                    setItemUnselected(position)
                    setButtonInactive()
                }
            }
        }
    }

    private fun setItemUnselected(position: Int) {
        with(bindingDialog) {
            when (position) {
                1 -> {
                    card1.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
                    text1.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                2 -> {
                    card2.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
                    text2.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                3 -> {
                    card3.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
                    text3.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                4 -> {
                    card4.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
                    text4.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
            }
        }
    }

    private fun setButtonInactive() {
        with(bindingDialog.button) {
            setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_white10_with_stroke))
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_next_gray, 0)
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            setOnClickListener(null)
        }
    }

}