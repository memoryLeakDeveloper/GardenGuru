package com.example.gardenguru.ui.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ItemExpandableListBinding

class SpinnerAdapter(private val listener: SpinnerLayout.SelectListener) :
    RecyclerView.Adapter<SpinnerAdapter.ExpandableViewHolder>() {

    private var lastSelectedItem: ExpandableViewHolder? = null
    var list: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        return ExpandableViewHolder(ItemExpandableListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
        holder.binding.textView.text = list[position]
        holder.binding.root.setOnClickListener { view ->
            listener.onSelect(holder.binding.textView.text.toString())
            lastSelectedItem?.binding?.root?.setBackgroundColor(ContextCompat.getColor(view.context, R.color.transparent))
            lastSelectedItem = holder
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.primary_green))
        }
    }

    fun setListAdapter(refreshList: ArrayList<String>) {
        list = refreshList
    }

    override fun getItemCount() = list.size

    class ExpandableViewHolder(val binding: ItemExpandableListBinding) : RecyclerView.ViewHolder(binding.root)
}