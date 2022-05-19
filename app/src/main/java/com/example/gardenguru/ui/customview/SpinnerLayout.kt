package com.example.gardenguru.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.ui.customview.SpinnerLayout.SelectListener
import com.google.android.material.divider.MaterialDivider

class SpinnerLayout(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var listString: ArrayList<String> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView
    private lateinit var arrowView: ImageView
    private lateinit var dividerView: MaterialDivider
    private lateinit var editText: EditText
    private var isEditText: Boolean = false
    private var isListExpanded = false
    private val selectListener = SelectListener {
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        textView.text = it
        hideList(isEditText)
    }
    private val editListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO && v.text.isNotEmpty()) {
            listString.add(v.text.toString())
            (recyclerView.adapter as SpinnerAdapter).list = listString
            (recyclerView.adapter as SpinnerAdapter).notifyItemInserted(listString.lastIndex)
            v.text = ""
        }
        false
    }

    fun interface SelectListener {
        fun onSelect(string: String)
    }

    init {
        inflate(context, R.layout.spinner_layout, this)
        this.translationZ = 50F
    }

    fun initView(defValue: String?, list: ArrayList<String>, isEditText: Boolean) {
        textView = findViewById(R.id.spinner_text)
        arrowView = findViewById(R.id.spinner_arrow)
        dividerView = findViewById(R.id.divider)
        editText = findViewById<EditText?>(R.id.edit_text).apply {
            setOnEditorActionListener(editListener)
            background = null
        }
        this.isEditText = isEditText
        this.listString = list
        if (defValue.isNullOrEmpty()) {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
            textView.text = "Введите текст"
        } else {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
            textView.text = defValue
        }
        recyclerView = findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = SpinnerAdapter(selectListener).apply {
                refreshAdapter(list)
            }
            layoutManager = LinearLayoutManager(context)
        }
        setListener()
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
        recyclerView.visibility = View.GONE
        dividerView.visibility = View.GONE
        isListExpanded = false
        setAnimation(true)
        if (isEditText) editText.visibility = View.GONE
//        val params = recyclerView.layoutParams
//        params.height = 0
//        recyclerView.layoutParams = params
    }

    private fun showList(isEditText: Boolean) {
        recyclerView.visibility = View.VISIBLE
        dividerView.visibility = View.VISIBLE
        isListExpanded = true
        setAnimation(false)
        if (isEditText) editText.visibility = View.VISIBLE
//        val params = recyclerView.layoutParams
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        recyclerView.layoutParams = params
    }

    private fun setAnimation(isUp: Boolean) {
        arrowView.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 250
            start()
        }
    }

}