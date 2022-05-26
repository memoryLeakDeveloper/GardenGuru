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
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.SpinnerLayoutBinding
import com.example.gardenguru.databinding.SpinnerPopupBinding
import com.example.gardenguru.ui.customview.spinner.SpinnerLayout.SelectListener

class SpinnerLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var binding: SpinnerLayoutBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var popupBinding: SpinnerPopupBinding
    private var popupWindow: PopupWindow? = null
    private var isListExpanded = false
    private val selectListener = SelectListener { text: String, close: Boolean ->
        binding.spinnerText.text = text
        if (close) popupWindow?.dismiss()
    }
    private val dismissListener = PopupWindow.OnDismissListener {
        binding.root.background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        isListExpanded = false
        arrowAnimation(true)
    }

    fun interface SelectListener {
        fun onSelect(string: String, onClose: Boolean)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerLayoutBinding.inflate(inflater, this)
        popupBinding = SpinnerPopupBinding.inflate(inflater)
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        setRootClickListener()
    }

    fun initView(defValue: String?, defPos: Int?, list: ArrayList<String>, isEditText: Boolean) {
        spinnerAdapter = SpinnerAdapter((selectListener)).apply { setListAdapter(list) }
        popupBinding.spinnerRecycler.adapter = spinnerAdapter
        popupBinding.spinnerRecycler.layoutManager = LinearLayoutManager(context)
        setSpinnerDefState(defValue, defPos, list)
        if (!isEditText) {
            setSpinnerBottomMargin()
        } else {
            initEditText()
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


    private fun setSpinnerBottomMargin() {
        val params = LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = (25F * context.resources.displayMetrics.density).toInt()
        popupBinding.spinnerRecycler.layoutParams = params
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
            ContextCompat.getDrawable(context, R.drawable.spinner_background),
            ContextCompat.getDrawable(context, R.drawable.spinner_selected_background)
        )
        val transitionDrawable = TransitionDrawable(backgrounds.toTypedArray())
        background = transitionDrawable
        transitionDrawable.startTransition(150)
    }

    private fun hideKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0);
    }

}