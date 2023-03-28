package com.entexy.gardenguru.ui.customview.spinner

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.SpinnerItemBinding
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible

class SpinnerAdapter(private val listener: (String, Int, Boolean) -> Unit, private var selectedPosition: Int = 0) :
    RecyclerView.Adapter<SpinnerAdapter.SpinnerAdapterViewHolder>() {

    private var lastSelectedPosition: Int = -1
    var isUpdating = false
    var list: MutableList<String> = mutableListOf()
    var textColor: ColorStateList? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerAdapterViewHolder {
        val binding = SpinnerItemBinding.inflate(LayoutInflater.from(parent.context)).also {
            it.root.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return SpinnerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerAdapterViewHolder, position: Int) {
        holder.binding.apply {
            if (position == selectedPosition) {
                textView.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.primary_green))
            } else {
                textView.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.white))
            }
            textView.toVisible()
            textView.text = list[position]
            textColor?.let { holder.binding.textView.setTextColor(it) }
            if (position == list.size - 1)
                divider.toGone()
            root.setOnClickListener { view ->
                textView.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.primary_green))
                listener.invoke(holder.binding.textView.text.toString(), position, true)
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.bindingAdapterPosition
            }
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


    fun setListAdapter(list: MutableList<String>) {
        this.list = list
    }

    fun setItemSelected(position: Int) {
        notifyItemChanged(selectedPosition)
        selectedPosition = position
    }

    override fun getItemCount() = list.size

    class SpinnerAdapterViewHolder(val binding: SpinnerItemBinding) : RecyclerView.ViewHolder(binding.root)
}