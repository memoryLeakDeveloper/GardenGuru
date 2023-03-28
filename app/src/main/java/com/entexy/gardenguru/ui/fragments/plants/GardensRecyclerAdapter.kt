package com.entexy.gardenguru.ui.fragments.plants

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.databinding.RvGardenItemBinding

class GardensRecyclerAdapter : RecyclerView.Adapter<GardensRecyclerAdapter.ViewHolder>() {

    private var gardenList = ArrayList<GardenData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvGardenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val garden = gardenList[position]
            tvGardenName.text = garden.name
            rvPlants.layoutManager = LinearLayoutManager(root.context)
            val adapter = PlantsRecyclerAdapter(garden.plants)
            rvPlants.adapter = adapter

            tvGardenOwner.visibility = View.GONE
            ivEditDelete.setImageResource(R.drawable.ic_edit)
            ivEditDelete.setOnClickListener {
//                root.findNavController().navigate(
//                    R.id.action_myPlantsFragment_to_gardenManagementFragment,
//                    bundleOf(GardenManagementFragment.GARDEN_EXTRA to garden)
//                )
            }

            spinnerArrow.setOnClickListener {
                if (rvPlants.visibility == View.VISIBLE) {
                    spinnerArrow.animate().rotation(180f).duration = 200
                    rvPlants.visibility = View.GONE
                } else {
                    spinnerArrow.animate().rotation(0f).duration = 200
                    rvPlants.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(gardenList: ArrayList<GardenData>) {
        this.gardenList = gardenList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return gardenList.size
    }

    class ViewHolder(val binding: RvGardenItemBinding) : RecyclerView.ViewHolder(binding.root)
}
