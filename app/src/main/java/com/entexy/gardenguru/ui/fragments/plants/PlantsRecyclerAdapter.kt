package com.entexy.gardenguru.ui.fragments.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.RvGardenPlantItemBinding

class PlantsRecyclerAdapter(private val plants: ArrayList<PlantData>) :
    RecyclerView.Adapter<PlantsRecyclerAdapter.ViewHolder>() {


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

            tvPlantName.text = item.customName
            tvPlantKind.text = item.variety

            if (position == itemCount - 1) {
                ivLine.visibility = View.GONE
            } else ivLine.visibility = View.VISIBLE
            root.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("PLANT_ID", plants[position].id)
                root.findNavController().navigate(
                    R.id.action_myPlantsFragment_to_plantCardFragment,
                    bundle
                )
            }
        }
    }

    class ViewHolder(val binding: RvGardenPlantItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return plants.size
    }
}
