package com.example.gardenguru.ui.my_plants.garden_managment

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.garden.models.Participant
import com.example.gardenguru.databinding.RvParticipantItemBinding

class ParticipantRecyclerAdapter(
    resources: Resources,
    private val participants: ArrayList<Participant>,
    private val viewModel: GardenManagementViewModel
) :
    RecyclerView.Adapter<ParticipantRecyclerAdapter.ViewHolder>() {

    private val spinnerValues = arrayListOf(
        resources.getString(Participant.RoleInGarden.Beginner.nameStringRes),
        resources.getString(Participant.RoleInGarden.Experienced.nameStringRes),
        resources.getString(Participant.RoleInGarden.Guru.nameStringRes)
    )

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

            val defPosition = when(participant.role){
                Participant.RoleInGarden.Beginner -> 0
                Participant.RoleInGarden.Experienced -> 1
                Participant.RoleInGarden.Guru -> 2
                else -> 0
            }

            spinner.initView(participant.email, defPosition, spinnerValues)
            spinner.setValueListener{ position: Int, name: String ->
                Toast.makeText(root.context, "role selected: $name", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun getItemCount(): Int {
        return participants.size
    }

    class ViewHolder(val binding: RvParticipantItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
