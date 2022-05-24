package com.example.gardenguru.ui.customview.card.pests

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.databinding.PestsItemBinding

class PestsCardAdapter(private val listPests: List<PestData>) : RecyclerView.Adapter<PestsCardAdapter.PestsCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PestsCardViewHolder {
        return PestsCardViewHolder(PestsItemBinding.inflate(LayoutInflater.from(parent.context)))
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

    class PestsCardViewHolder(val binding: PestsItemBinding) : RecyclerView.ViewHolder(binding.root)
}