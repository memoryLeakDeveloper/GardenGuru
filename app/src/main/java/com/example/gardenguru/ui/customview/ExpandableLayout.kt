package com.example.gardenguru.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
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
    private var isListExpanded = false
    private val listener = SelectListener {
        background = AppCompatResources.getDrawable(context, R.drawable.spinner_background)
        textView.text = it
        hideList()
    }

    fun interface SelectListener {
        fun onSelect(string: String)
    }

    fun initView(defValue: String?, list: List<String>) {
        background = AppCompatResources.getDrawable(
            context,
            if (defValue.isNullOrEmpty()) R.drawable.spinner_background_unselected else R.drawable.spinner_background
        )
//        textView = findViewById(R.id.spinner_text)
//        arrowView = findViewById(R.id.spinner_arrow)
//        dividerView = findViewById(R.id.divider)
//        recyclerView = findViewById<RecyclerView>(R.id.recycler).apply {
//            adapter = ExpandableAdapter(list, listener)
//            layoutManager = LinearLayoutManager(context)
//        }
        setListener()
    }

    private fun setListener() {
        this.setOnClickListener {
            if (!isListExpanded)
                showList()
            else
                hideList()
        }
    }

    private fun hideList() {
        recyclerView.visibility = View.GONE
        dividerView.visibility = View.GONE
        isListExpanded = false
        setAnimation(true)
    }

    private fun showList() {
        recyclerView.visibility = View.VISIBLE
        dividerView.visibility = View.VISIBLE
        isListExpanded = true
        setAnimation(false)
    }

    private fun setAnimation(isUp: Boolean) {
        arrowView.animate().apply {
            rotation(if (isUp) 0F else 180F)
            duration = 500
            start()
        }
    }

}