package com.example.gardenguru.ui.customview.spinner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.SpinnerItemBinding

class SpinnerAdapter(private val listener: SpinnerLayout.SelectListener) :
    RecyclerView.Adapter<SpinnerAdapter.SpinnerAdapterViewHolder>() {

    private var lastSelectedPosition: Int = -1
    private var selectedPosition: Int = -1
    var isUpdating = false
    var list: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerAdapterViewHolder {
        val binding = SpinnerItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return SpinnerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerAdapterViewHolder, position: Int) {
        if (position == selectedPosition) {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.primary_green))
        } else {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.transparent))
        }
        holder.binding.textView.visibility = View.VISIBLE
        holder.binding.textView.text = list[position]
        holder.binding.root.setOnClickListener { view ->
            listener.onSelect(holder.binding.textView.text.toString(), position, true)
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.bindingAdapterPosition
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.primary_green))
        }
    }

    fun clearItemFocus() {
        lastSelectedPosition = selectedPosition
        notifyItemChanged(lastSelectedPosition)
        selectedPosition = -1
    }

    fun setItemFocus() {
        if (isUpdating) {
            isUpdating = false
            return
        }
        selectedPosition = lastSelectedPosition
        notifyItemChanged(selectedPosition)
    }

    fun insertNewItem(item: String) {
        isUpdating = true
        list.add(item)
        selectedPosition = list.size - 1
        notifyItemInserted(selectedPosition)
    }

    fun setListAdapter(list: ArrayList<String>) {
        this.list = list
    }

    override fun getItemCount() = list.size

    class SpinnerAdapterViewHolder(val binding: SpinnerItemBinding) : RecyclerView.ViewHolder(binding.root)
}