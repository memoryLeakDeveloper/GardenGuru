package com.example.gardenguru.ui.customview.spinner.checkbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.databinding.SpinnerCheckboxItemBinding

class SpinnerCheckboxAdapter(private val list: List<String>, private val listener: SpinnerCheckboxLayout.SelectListener) :
    RecyclerView.Adapter<SpinnerCheckboxAdapter.SpinnerAdapterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerAdapterViewHolder {
        val binding = SpinnerCheckboxItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return SpinnerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerAdapterViewHolder, position: Int) {
        holder.binding.textView.text = list[position]
    }

    override fun getItemCount() = list.size

    class SpinnerAdapterViewHolder(val binding: SpinnerCheckboxItemBinding) : RecyclerView.ViewHolder(binding.root)
}