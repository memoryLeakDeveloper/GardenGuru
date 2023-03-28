package com.entexy.gardenguru.ui.customview.spinner.textview

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.SpinnerLabelLayoutBinding
import com.entexy.gardenguru.databinding.SpinnerPopupBinding
import com.entexy.gardenguru.ui.customview.spinner.SpinnerAdapter
import com.entexy.gardenguru.ui.customview.spinner.SpinnerLayout
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class SpinnerLabelLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var binding: SpinnerLabelLayoutBinding
    private var spinnerAdapter: SpinnerAdapter
    private var popupBinding: SpinnerPopupBinding
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private var list: ArrayList<String> = arrayListOf()
    private val selectListener: (String, Int, Boolean) -> Unit = { text: String, position: Int, close: Boolean ->
        binding.spinnerText.text = text
        valueCallback?.value(position, text)
        spinnerValue = text
        if (close) popupWindow?.dismiss()
    }
    private val dismissListener = PopupWindow.OnDismissListener {
        binding.root.background = AppCompatResources.getDrawable(
            context,
           R.drawable.spinner_background
        )
        isListExpanded = false
        arrowAnimation(true)
    }
    private var valueCallback: ValueCallback? = null

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    var spinnerValue: String? = null
        private set

    fun interface ValueCallback {
        fun value(position: Int, name: String)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerLabelLayoutBinding.inflate(inflater, this)
        popupBinding = SpinnerPopupBinding.inflate(inflater)
        spinnerAdapter = SpinnerAdapter(selectListener)
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        setRootClickListener()
        context.obtainStyledAttributes(attrs, R.styleable.SpinnerLayout, 0, 0).apply {
            getColorStateList(R.styleable.SpinnerLayout_hint_color)?.let { binding.spinnerText.setTextColor(it) }
            getColorStateList(R.styleable.SpinnerLayout_text_color)?.let {
                spinnerAdapter.textColor = it
                popupBinding.editText.setHintTextColor(it)
                popupBinding.editText.setTextColor(it)
            }
            recycle()
        }
    }

    fun initView(label: String, defPos: Int, list: ArrayList<String>, isEditText: Boolean = false) {
        binding.labeltext.text = label
        spinnerAdapter.setListAdapter(list)
        popupBinding.spinnerRecycler.adapter = spinnerAdapter
        popupBinding.spinnerRecycler.layoutManager = LinearLayoutManager(context)
        setSpinnerDefState(defPos, list)
        if (!isEditText) {
            setSpinnerBottomMargin()
        } else {
            initEditText()
        }

        this.list = list
    }

    fun setItemSelected(position: Int): Boolean {
        if (position !in 0 until list.size) return false
        binding.spinnerText.text = list[position]
        spinnerValue = list[position]
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        spinnerAdapter.setItemSelected(position)
        popupWindow?.dismiss()
        return true
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
        val params = LinearLayoutCompat.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = (25F * context.resources.displayMetrics.density).toInt()
        popupBinding.spinnerRecycler.layoutParams = params
    }

    private fun setSpinnerDefState(defPos: Int?, list: ArrayList<String>) {
        if ((defPos != null) && (defPos >= 0)) {
            binding.spinnerText.text = list[defPos]
            spinnerValue = list[defPos]
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        } else {
            binding.spinnerText.text = "Введите"
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
        }
    }

    private fun initEditText() {
        popupBinding.editText.apply {
            visibility = View.VISIBLE
            background = null
            setOnEditorActionListener { editText, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO && editText.text.isNotEmpty()) {
                    editText.text.toString().let {
                        popupBinding.editText.setText(it)
                        spinnerAdapter.insertNewItem(it)
                        binding.spinnerText.text = it
                    }
                    editText.text = ""
                    hideKeyboard(editText as EditText)
                }
                false
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    spinnerAdapter.clearItemFocus()
                    setBackgroundColor(ContextCompat.getColor(context, R.color.primary_green))
                } else {
                    spinnerAdapter.setItemFocus()
                    setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                }
            }
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
        val backgrounds = listOf(
            ContextCompat.getDrawable(
                context,
                R.drawable.spinner_background
            ),
            ContextCompat.getDrawable(
                context, R.drawable.spinner_selected_background
            )
        )
        val transitionDrawable = TransitionDrawable(backgrounds.toTypedArray())
        background = transitionDrawable
        transitionDrawable.startTransition(150)
    }

    private fun hideKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
