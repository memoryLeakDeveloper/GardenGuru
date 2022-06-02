package com.example.gardenguru.ui.my_plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.garden.models.GardenPlantData
import com.example.gardenguru.databinding.RvGardenPlantItemBinding

class PlantsRecyclerAdapter(private val plants: ArrayList<GardenPlantData>) :
    RecyclerView.Adapter<PlantsRecyclerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvGardenPlantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = plants[position]

            Glide.with(root.context)
                .load(item.photo)
                .centerCrop()
                .placeholder(R.drawable.image_plasholder)
                .into(ivPlantImage)

            tvPlantName.text = item.name
            tvPlantKind.text = item.plant

            if (position == itemCount - 1){
                ivLine.visibility = View.GONE
            }else ivLine.visibility = View.VISIBLE
            root.setOnClickListener{
                root.findNavController().navigate(R.id.action_myPlantsFragment_to_plantCardFragment) //add plant
            }
        }
    }

    class ViewHolder(val binding: RvGardenPlantItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return plants.size
    }
}
