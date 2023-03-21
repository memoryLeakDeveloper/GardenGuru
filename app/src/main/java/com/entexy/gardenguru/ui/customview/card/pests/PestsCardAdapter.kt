package com.entexy.gardenguru.ui.customview.card.pests

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.data.pest.PestData
import com.entexy.gardenguru.databinding.ItemPestsBinding

class PestsCardAdapter(private val listPests: List<PestData>) : RecyclerView.Adapter<PestsCardAdapter.PestsCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PestsCardViewHolder {
        return PestsCardViewHolder(ItemPestsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PestsCardViewHolder, position: Int) {
        holder.binding.pest.apply {
            text = listPests[position].name
            setOnClickListener {
                //TODO
                Toast.makeText(context, listPests[position].name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = listPests.size

    class PestsCardViewHolder(val binding: ItemPestsBinding) : RecyclerView.ViewHolder(binding.root)
}