package com.entexy.gardenguru.ui.customview.spinner.checkbox

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.SpinnerCheckboxLayoutBinding
import com.entexy.gardenguru.databinding.SpinnerCheckboxPopupBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class SpinnerCheckboxLayout(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private lateinit var binding: SpinnerCheckboxLayoutBinding
    private var popupBinding: SpinnerCheckboxPopupBinding
    private lateinit var spinnerAdapter: SpinnerCheckboxAdapter
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private val dismissListener = PopupWindow.OnDismissListener {
        binding.root.background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
        isListExpanded = false
        arrowAnimation(true)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerCheckboxLayoutBinding.inflate(inflater, this)
        popupBinding = SpinnerCheckboxPopupBinding.inflate(inflater)
        spinnerAdapter = SpinnerCheckboxAdapter()
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        setRootClickListener()
        context.obtainStyledAttributes(attrs, R.styleable.SpinnerCheckboxLayout, 0, 0).apply {
            getColorStateList(R.styleable.SpinnerCheckboxLayout_hint_color_check)?.let { binding.spinnerText.setTextColor(it) }
            getColorStateList(R.styleable.SpinnerCheckboxLayout_text_color_check)?.let { spinnerAdapter.textColor = it }
            recycle()
        }
    }

    fun initView(defValue: String, list: ArrayList<String>) {
        spinnerAdapter.list = list
        popupBinding.recycler.adapter = spinnerAdapter
        popupBinding.recycler.layoutManager = LinearLayoutManager(context)
        setSpinnerDefState(defValue)
    }

    private fun setRootClickListener() {
        this.setOnClickListener {
            if (isListExpanded) {
                popupWindow?.dismiss()
                isListExpanded = false
                arrowAnimation(true)
            } else {
                if (popupWindow == null) {
                    initPopupWindow(it)
                }
                if (!isListExpanded) {
                    backgroundAnimation()
                    popupWindow!!.showAsDropDown(it, 0, 0)
                    arrowAnimation(false)
                    isListExpanded = true
                }
            }
        }
    }

    private fun initPopupWindow(view: View) {
        popupWindow = PopupWindow(view.width, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setOnDismissListener(dismissListener)
            setBackgroundDrawable(AppCompatResources.getDrawable(context, R.drawable.spinner_background_expanded))
            isOutsideTouchable = true
            isFocusable = true
            contentView = popupBinding.root
        }
    }

    private fun setSpinnerDefState(defString: String) {
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
        binding.spinnerText.text = defString
    }

    private fun arrowAnimation(isUp: Boolean) {
        binding.spinnerArrow.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 250
            start()
        }
    }

    private fun backgroundAnimation() {
        val backgrounds = listOf(
            ContextCompat.getDrawable(context, R.drawable.spinner_background_unselected),
            ContextCompat.getDrawable(context, R.drawable.spinner_selected_background)
        )
        val transitionDrawable = TransitionDrawable(backgrounds.toTypedArray())
        background = transitionDrawable
        transitionDrawable.startTransition(150)
    }
}