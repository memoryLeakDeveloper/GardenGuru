package com.example.gardenguru.ui.customview.spinner

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.SpinnerLayoutBinding
import com.example.gardenguru.databinding.SpinnerPopupBinding
import com.example.gardenguru.ui.customview.spinner.SpinnerLayout.SelectListener
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class SpinnerLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = SpinnerLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var popupBinding = SpinnerPopupBinding.inflate(LayoutInflater.from(context))
    private var spinnerAdapter: SpinnerAdapter
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private var defValue: String? = null
    private var list: ArrayList<String> = arrayListOf()
    private val selectListener = SelectListener { text: String, position: Int, close: Boolean ->
        binding.spinnerText.text = text
        valueCallback?.value(position, text)
        spinnerValue = text
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
    private var valueCallback: ValueCallback? = null

    private var newItemCallback: NewItemCallback? = null

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    fun setNewItemListener(callback: NewItemCallback) {
        newItemCallback = callback
    }

    var spinnerValue: String? = null
        private set

    fun interface ValueCallback {
        fun value(position: Int, name: String)
    }

    fun interface NewItemCallback {
        fun newItemAdded(position: Int, text: String)
    }

    fun interface SelectListener {
        fun onSelect(string: String, position: Int, onClose: Boolean)
    }

    init {
        spinnerAdapter = SpinnerAdapter((selectListener))
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        setRootClickListener()
        setCustomAttributes(attrs)
    }

    fun initView(defValue: String?, defPos: Int?, list: ArrayList<String>, isEditText: Boolean = false) {
        spinnerAdapter.setListAdapter(list)
        popupBinding.spinnerRecycler.adapter = spinnerAdapter
        popupBinding.spinnerRecycler.layoutManager = LinearLayoutManager(context)
        setSpinnerDefState(defValue, defPos, list)
        if (isEditText) {
            initEditText()
        }
        this.defValue = defValue
        this.list = list
    }

    fun setItemSelected(position: Int): Boolean {
        if (position !in 0 until list.size) return false
        binding.spinnerText.text = list[position]
        spinnerValue = list[position]
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        valueCallback?.value(position, list[position])
        spinnerAdapter.setItemSelected(position)
        popupWindow?.dismiss()
        return true
    }

    fun deleteLastItem() {
        spinnerAdapter.deleteLastItem()
        selectListener.onSelect(this.defValue!!, 0, true)
    }

    private fun setCustomAttributes(attrs: AttributeSet) {
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

    private fun setSpinnerDefState(defString: String?, defPos: Int?, list: ArrayList<String>) {
        if (defString.isNullOrEmpty()) {
            if ((defPos != null) && (defPos >= 0)) {
                binding.spinnerText.text = list[defPos]
                spinnerValue = list[defPos]
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
                        newItemCallback?.newItemAdded(spinnerAdapter.itemCount, it)
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
        val bool = isDefValue(binding.spinnerText)
        val backgrounds = listOf(
            ContextCompat.getDrawable(context, if (!bool) R.drawable.spinner_background else R.drawable.spinner_background_unselected),
            ContextCompat.getDrawable(context, R.drawable.spinner_selected_background)
        )
        val transitionDrawable = TransitionDrawable(backgrounds.toTypedArray())
        background = transitionDrawable
        transitionDrawable.startTransition(150)
    }

    private fun isDefValue(textView: TextView) = textView.text == defValue

    private fun hideKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}