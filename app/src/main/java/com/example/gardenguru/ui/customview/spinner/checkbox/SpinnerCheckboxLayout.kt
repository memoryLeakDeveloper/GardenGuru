package com.example.gardenguru.ui.customview.spinner.checkbox

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.SpinnerCheckboxLayoutBinding
import com.example.gardenguru.databinding.SpinnerCheckboxPopupBinding
import com.example.gardenguru.ui.customview.spinner.checkbox.SpinnerCheckboxLayout.SelectListener
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class SpinnerCheckboxLayout : LinearLayoutCompat {

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    private lateinit var binding: SpinnerCheckboxLayoutBinding
    private lateinit var popupBinding: SpinnerCheckboxPopupBinding
    private lateinit var spinnerAdapter: SpinnerCheckboxAdapter
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private var defValue: String? = null
    private val selectListener = SelectListener { text: String, position: Int, close: Boolean ->
        binding.spinnerText.text = text
        if (close) popupWindow?.dismiss()
    }
    private val dismissListener = PopupWindow.OnDismissListener {
        binding.root.background = AppCompatResources.getDrawable(
            context,
            if (!isDefValue(binding.spinnerText)) R.drawable.spinner_background else R.drawable.spinner_background_unselected
        )
        isListExpanded = false
        arrowAnimation(true)
    }

    fun interface SelectListener {
        fun onSelect(string: String, position: Int, onClose: Boolean)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerCheckboxLayoutBinding.inflate(inflater, this)
        popupBinding = SpinnerCheckboxPopupBinding.inflate(inflater)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        setRootClickListener()
    }

    fun initView(defValue: String?, defPos: Int?, list: ArrayList<String>) {
        spinnerAdapter = SpinnerCheckboxAdapter(list.toList(), selectListener)
        popupBinding.recycler.adapter = spinnerAdapter
        popupBinding.recycler.layoutManager = LinearLayoutManager(context)
        setSpinnerDefState(defValue, defPos, list)
//        setSpinnerBottomMargin()
        this.defValue = defValue
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


    private fun setSpinnerBottomMargin() {
        val params = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = (25F * context.resources.displayMetrics.density).toInt()
        popupBinding.recycler.layoutParams = params
    }

    private fun setSpinnerDefState(defString: String?, defPos: Int?, list: ArrayList<String>) {
        if (defString.isNullOrEmpty()) {
            if ((defPos != null) && (defPos >= 0)) {
                binding.spinnerText.text = list[defPos]
                background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
            } else {
                binding.spinnerText.text = "Введите"
                background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
            }
        } else {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
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

    private fun backgroundAnimation() {
        val bool = isDefValue(binding.spinnerText)
        val backgrounds = listOf(
            ContextCompat.getDrawable(context, if (!bool) R.drawable.spinner_background else R.drawable.spinner_background_unselected),
            ContextCompat.getDrawable(
                context,
                if (!bool) R.drawable.spinner_selected_background else R.drawable.spinner_unselected_background
            )
        )
        val transitionDrawable = TransitionDrawable(backgrounds.toTypedArray())
        background = transitionDrawable
        transitionDrawable.startTransition(150)
    }

    private fun isDefValue(textView: TextView) = textView.text == defValue

}