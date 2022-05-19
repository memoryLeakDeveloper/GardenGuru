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
import com.example.gardenguru.ui.customview.ExpandableLayout.SelectListener
import com.google.android.material.divider.MaterialDivider

class ExpandableLayout(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView
    private lateinit var arrowView: ImageView
    private lateinit var dividerView: MaterialDivider
    private lateinit var editText: EditText
    private var isEditText: Boolean = false
    private var listString: ArrayList<String> = ArrayList()
    private var isListExpanded = false
    private val selectListener = SelectListener {
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        textView.text = it
        hideList(isEditText)
    }
    private val editListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO && v.text.isNotEmpty()) {
            listString.add(v.text.toString())
            (recyclerView.adapter as ExpandableLayoutAdapter).list = listString
            (recyclerView.adapter as ExpandableLayoutAdapter).notifyItemInserted(listString.lastIndex)
            v.text = ""
        }
        false
    }

    fun interface SelectListener {
        fun onSelect(string: String)
    }

    fun initView(defValue: String?, list: ArrayList<String>, isEditText: Boolean) {
        textView = findViewById(R.id.spinner_text)
        arrowView = findViewById(R.id.spinner_arrow)
        dividerView = findViewById(R.id.divider)
        editText = findViewById(R.id.edit_text)
        editText.setOnEditorActionListener(editListener)
        this.isEditText = isEditText
        this.listString = list
        if (defValue.isNullOrEmpty()) {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background_unselected)
            textView.text = "Введите текст"
        } else {
            background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
            textView.text = "Введите текст"
        }
        recyclerView = findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = ExpandableLayoutAdapter(selectListener).apply {
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
//        val params = recyclerView.layoutParams
//        params.height = 0
//        recyclerView.layoutParams = params
        dividerView.visibility = View.GONE
        isListExpanded = false
        setAnimation(true)
        if (isEditText) editText.visibility = View.GONE
    }

    private fun showList(isEditText: Boolean) {
        recyclerView.visibility = View.VISIBLE
//        val params = recyclerView.layoutParams
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        recyclerView.layoutParams = params
        dividerView.visibility = View.VISIBLE
        isListExpanded = true
        setAnimation(false)
        if (isEditText) editText.apply {
            visibility = View.VISIBLE

        }
    }

    private fun setAnimation(isUp: Boolean) {
        arrowView.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 250
            start()
        }
    }

}