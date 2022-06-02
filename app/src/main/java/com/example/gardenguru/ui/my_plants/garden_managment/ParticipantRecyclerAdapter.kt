package com.example.gardenguru.ui.my_plants.garden_managment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.garden.models.Participant
import com.example.gardenguru.databinding.RvParticipantItemBinding

class ParticipantRecyclerAdapter(private val participants: ArrayList<Participant>, private val viewModel: GardenManagementViewModel) :
    RecyclerView.Adapter<ParticipantRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvParticipantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val participant = participants[position]

//            spinner.initView()
        }
    }


    override fun getItemCount(): Int {
        return participants.size
    }

    class ViewHolder(val binding: RvParticipantItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
