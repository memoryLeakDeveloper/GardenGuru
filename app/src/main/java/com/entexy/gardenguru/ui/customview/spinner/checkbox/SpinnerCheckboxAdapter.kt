package com.entexy.gardenguru.ui.customview.spinner.checkbox

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.SpinnerCheckboxItemBinding
import com.entexy.gardenguru.utils.Extensions.setDrawable

class SpinnerCheckboxAdapter : RecyclerView.Adapter<SpinnerCheckboxAdapter.SpinnerAdapterViewHolder>() {

    var list: List<String> = emptyList()
    var selectedItems: ArrayList<String> = arrayListOf()
    var textColor: ColorStateList? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerAdapterViewHolder {
        val binding = SpinnerCheckboxItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return SpinnerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerAdapterViewHolder, position: Int) {
        with(holder.binding) {
            textView.text = list[position]
            textColor?.let { textView.setTextColor(it) }
            root.setOnClickListener {
                if (selectedItems.contains(textView.text)) {
                    checkbox.setDrawable(R.drawable.ic_checkbox_off)
                    selectedItems.remove(textView.text)
                } else {
                    checkbox.setDrawable(R.drawable.ic_checkbox_on)
                    selectedItems.add(textView.text.toString())
                }
            }
        }
    }

    override fun getItemCount() = list.size

    class SpinnerAdapterViewHolder(val binding: SpinnerCheckboxItemBinding) : RecyclerView.ViewHolder(binding.root)
}