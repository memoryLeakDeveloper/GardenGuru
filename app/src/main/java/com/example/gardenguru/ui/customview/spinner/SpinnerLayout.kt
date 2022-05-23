package com.example.gardenguru.ui.customview.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.SpinnerLayoutBinding
import com.example.gardenguru.ui.customview.spinner.SpinnerLayout.SelectListener

class SpinnerLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var binding: SpinnerLayoutBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var listString: ArrayList<String> = ArrayList()
    private var isEditText: Boolean = false
    private var isListExpanded = false
    private val selectListener = SelectListener {
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        binding.spinnerText.text = it
        hideList(isEditText)
    }
    private val editListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO && v.text.isNotEmpty()) {
            listString.add(v.text.toString())
            spinnerAdapter.list = listString
            spinnerAdapter.notifyItemInserted(listString.lastIndex)
            v.text = ""
        }
        false
    }

    fun interface SelectListener {
        fun onSelect(string: String)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerLayoutBinding.inflate(inflater, this)
        translationZ = 50F
        orientation = VERTICAL
        spinnerAdapter = SpinnerAdapter((selectListener))
        binding.editText.apply {
            setOnEditorActionListener(editListener)
            background = null
        }
        setListener()
    }

    fun initView(defValue: String?, list: ArrayList<String>, isEditText: Boolean) {
        this.isEditText = isEditText
        this.listString = list
        if (defValue.isNullOrEmpty()) {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
            binding.spinnerText.text = "Введите текст"
        } else {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
            binding.spinnerText.text = defValue
        }
        binding.recycler.apply {
            adapter = spinnerAdapter.apply {
                setListAdapter(list)
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setListener() {
        this.setOnClickListener {
            if (!isListExpanded)
                showList(isEditText)
            else
                hideList(isEditText)
        }
    }

    private fun hideList(isEditText: Boolean) {
        binding.recycler.visibility = View.GONE
        binding.divider.visibility = View.GONE
        isListExpanded = false
        setAnimation(true)
        if (isEditText) binding.editText.visibility = View.GONE
//        val params = recyclerView.layoutParams
//        params.height = 0
//        recyclerView.layoutParams = params
    }

    private fun showList(isEditText: Boolean) {
        binding.divider.visibility = View.VISIBLE
        binding.recycler.visibility = View.VISIBLE
        isListExpanded = true
        setAnimation(false)
        if (isEditText) binding.editText.visibility = View.VISIBLE
//        val params = recyclerView.layoutParams
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        recyclerView.layoutParams = params
    }

    private fun setAnimation(isUp: Boolean) {
        binding.spinnerArrow.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 250
            start()
        }
    }

}