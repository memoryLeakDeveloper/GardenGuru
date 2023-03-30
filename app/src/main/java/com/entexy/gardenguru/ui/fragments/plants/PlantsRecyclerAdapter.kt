package com.entexy.gardenguru.ui.fragments.plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.RvGardenPlantItemBinding
import com.entexy.gardenguru.ui.fragments.plant_card.PlantCardFragment

class PlantsRecyclerAdapter :
    RecyclerView.Adapter<PlantsRecyclerAdapter.ViewHolder>() {


    private var plants: ArrayList<PlantData> = arrayListOf()

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
                .load(item.getPlantPhoto())
                .centerCrop()
                .placeholder(R.drawable.image_plasholder)
                .into(ivPlantImage)

            tvPlantName.text = item.name
            tvPlantKind.text = item.variety

            root.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(PlantCardFragment.PLANT_CARD_PLANT_DATA_KEY, plants[position])
                root.findNavController().navigate(
                    R.id.action_myPlantsFragment_to_plantCardFragment,
                    bundle
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(plants: ArrayList<PlantData>) {
        this.plants = plants
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: RvGardenPlantItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return plants.size
    }
}
