package com.entexy.gardenguru.ui.customview.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.SpinnerLayoutBinding
import com.entexy.gardenguru.databinding.SpinnerPopupBinding
import com.entexy.gardenguru.utils.hideKeyboard
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class SpinnerLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = SpinnerLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var popupBinding = SpinnerPopupBinding.inflate(LayoutInflater.from(context))
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private var defValue: String? = null
    private var list: List<String> = listOf()
    private val selectListener: (String, Int, Boolean) -> Unit = { text: String, position: Int, close: Boolean ->
        binding.spinnerText.text = text
        valueCallback?.value(position, text)
        spinnerValue = text
        if (close) popupWindow?.dismiss()
    }
    private val dismissListener = PopupWindow.OnDismissListener {
        if (defValue != null && list.contains(spinnerValue))
            binding.root.background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        isListExpanded = false
        arrowAnimation(true)
    }
    private var valueCallback: ValueCallback? = null

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    var spinnerValue: String? = null
        private set

    fun getSelectedValueIndex(): Int =
        list.indexOf(spinnerValue)

    fun interface ValueCallback {
        fun value(position: Int, name: String)
    }

    init {
        setRootClickListener()
        setCustomAttributes(attrs)
    }

    fun initView(defValue: String?, list: List<String>) {
        spinnerAdapter = SpinnerAdapter((selectListener), if (defValue != null) list.indexOf(defValue) else -1)
        spinnerAdapter.setListAdapter(list.toMutableList())
        popupBinding.spinnerRecycler.adapter = spinnerAdapter
        popupBinding.spinnerRecycler.layoutManager = LinearLayoutManager(context)
        setBackgroundCompat(
            ContextCompat.getDrawable(
                context,
                if (defValue != null && list.contains(defValue)
                ) R.drawable.spinner_background else R.drawable.edit_text_background_unfocused
            )
        )
        setSpinnerDefState(defValue, list)
        this.defValue = defValue
        this.list = list
    }

    private fun setCustomAttributes(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.SpinnerLayout, 0, 0).apply {
            getColorStateList(R.styleable.SpinnerLayout_hint_color)?.let { binding.spinnerText.setTextColor(it) }
            getColorStateList(R.styleable.SpinnerLayout_text_color)?.let { spinnerAdapter.textColor = it }
            recycle()
        }
    }

    private fun setRootClickListener() {
        this.setOnClickListener {
            hideKeyboard()
            if (isListExpanded) {
                popupWindow?.dismiss()
                isListExpanded = false
                arrowAnimation(true)
            } else {
                if (popupWindow == null) {
                    initPopupWindow(it)
                }
                if (!isListExpanded) {
                    popupWindow!!.showAsDropDown(it, 0, 20)
                    arrowAnimation(false)
                    isListExpanded = true
                }
            }
        }
    }

    private fun initPopupWindow(view: View) {
        popupWindow = PopupWindow(view.width, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setOnDismissListener(dismissListener)
            setBackgroundDrawable(AppCompatResources.getDrawable(context, R.drawable.spinner_background))
            isOutsideTouchable = true
            isFocusable = true
            contentView = popupBinding.root
            showAsDropDown(this@SpinnerLayout, 0, 20)
        }
    }

    private fun setSpinnerDefState(defString: String?, list: List<String>) {
        val defPos = 0
        if (defString.isNullOrEmpty()) {
            binding.spinnerText.text = list[defPos]
            spinnerValue = list[defPos]
        } else {
            binding.spinnerText.text = defString
        }
    }

    private fun arrowAnimation(isUp: Boolean) {
        binding.spinnerArrow.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 250
            start()
        }
    }

    fun hidePopup() {
        popupWindow?.dismiss()
    }

}