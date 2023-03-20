package com.example.gardenguru.ui.fragments.my_plants

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.DialogRemoveGardenBinding
import com.example.gardenguru.databinding.RvGardenItemBinding
import com.example.gardenguru.ui.customview.DialogHelper
import com.example.gardenguru.ui.fragments.my_plants.garden_managment.GardenManagementFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GardensRecyclerAdapter(private val viewModel: MyPlantsViewModel) :
    RecyclerView.Adapter<GardensRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvGardenItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val garden = viewModel.gardens.value!![position]

            tvGardenName.text = garden.name
            rvPlants.layoutManager = LinearLayoutManager(root.context)
            val adapter = PlantsRecyclerAdapter(garden.plants)
            rvPlants.adapter = adapter

            val guruEmail = garden.getGuruEmail()
            if (guruEmail == viewModel.userEmail) { //this so this is the user's garden
                tvGardenOwner.visibility = View.GONE
                ivEditDelete.setImageResource(R.drawable.ic_edit)
                ivEditDelete.setOnClickListener {
                    root.findNavController().navigate(
                        R.id.action_myPlantsFragment_to_gardenManagementFragment,
                        bundleOf(GardenManagementFragment.GARDEN_EXTRA to garden)
                    )
                }
            } else {
                tvGardenOwner.visibility = View.VISIBLE
                tvGardenOwner.text = root.resources.getString(R.string.garden_owner_name, guruEmail)
                ivEditDelete.setImageResource(R.drawable.ic_delete)
                ivEditDelete.setOnClickListener {
                    //delete

                    val dialogHelper = DialogHelper()
                    val dialogBinding = DialogRemoveGardenBinding.inflate(LayoutInflater.from(root.context))
                    with(dialogBinding) {

                        tvDialogDescription.text = root.resources.getString(
                            R.string.dialog_want_to_leave_garden,
                            garden.name,
                            guruEmail
                        )

                        btNo.setOnClickListener {
                            dialogHelper.hideDialog()
                        }
                        btYes.setOnClickListener {
                            //todo
                            CoroutineScope(Dispatchers.Main).launch {
                                viewModel.leaveGarden(garden.id)

                                dialogHelper.hideDialog()
                            }
                        }
                    }
                    dialogHelper.showDialog(dialogBinding.root)
                }
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


    override fun getItemCount(): Int {
        return viewModel.gardens.value?.size ?: 0
    }

    class ViewHolder(val binding: RvGardenItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}