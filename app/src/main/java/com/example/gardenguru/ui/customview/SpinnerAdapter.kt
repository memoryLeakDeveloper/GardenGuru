package com.example.gardenguru.ui.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ItemSpinnerBinding

class SpinnerAdapter(private val list: List<String>, private val listener: SpinnerLinearLayout.SelectListener) :
    RecyclerView.Adapter<SpinnerAdapter.SpinnerViewHolder>() {

    private var lastSelectedItem: SpinnerViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerViewHolder {
        return SpinnerViewHolder(ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SpinnerViewHolder, position: Int) {
        holder.binding.textView.text = list[position]
        holder.binding.root.setOnClickListener { view ->
            listener.onSelect(holder.binding.textView.text.toString())
            lastSelectedItem?.binding?.root?.setBackgroundColor(ContextCompat.getColor(view.context, R.color.transparent))
            lastSelectedItem = holder
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.primary_green))
        }
    }

    override fun getItemCount() = list.size

    class SpinnerViewHolder(val binding: ItemSpinnerBinding) : RecyclerView.ViewHolder(binding.root)
}