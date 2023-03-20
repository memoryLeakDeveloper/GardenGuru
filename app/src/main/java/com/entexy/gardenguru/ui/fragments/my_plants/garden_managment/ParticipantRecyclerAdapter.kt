package com.entexy.gardenguru.ui.fragments.my_plants.garden_managment

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.garden.models.Participant
import com.entexy.gardenguru.databinding.RvParticipantItemBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParticipantRecyclerAdapter(
    resources: Resources,
    private val participants: MutableList<Participant>,
    private val viewModel: GardenManagementViewModel
) :
    RecyclerView.Adapter<ParticipantRecyclerAdapter.ViewHolder>() {

    private val spinnerValues = arrayListOf(
        resources.getString(Participant.RoleInGarden.Beginner.nameStringRes),
        resources.getString(Participant.RoleInGarden.Experienced.nameStringRes),
        resources.getString(Participant.RoleInGarden.Guru.nameStringRes),
        resources.getString(R.string.delete)
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
    override fun onBindViewHolder(holder: ViewHolder, holderPosition: Int) {
        with(holder.binding) {
            val participant = participants[holderPosition]

            val defPosition = when (participant.role) {
                Participant.RoleInGarden.Beginner -> 0
                Participant.RoleInGarden.Experienced -> 1
                Participant.RoleInGarden.Guru -> 2
                else -> 0
            }

            spinner.initView(participant.email, defPosition, spinnerValues)
            spinner.setValueListener { position: Int, name: String ->

                spinner.isEnabled = false

                CoroutineScope(Dispatchers.Main).launch {
                    if (position == 3) {

                        val dialogHelper = DialogHelper()

                        val progressView = ProgressBar(root.context)
                        dialogHelper.showDialog(progressView, false)

                        val isSuccess = viewModel.deleteParticipant(participant.id)

                        dialogHelper.hideDialog()

                        if (!isSuccess){
                            spinner.setItemSelected(defPosition)
                            Toast.makeText(root.context, R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                        }else{
                            participants.removeAt(holderPosition)
                            notifyItemRemoved(holderPosition)
                        }

                    } else {
                        val isSuccess = viewModel.editParticipantRole(
                            participant.id,
                            Participant.getRoleByStringValue(name, root.resources).value
                        )
                        if (!isSuccess) {
                            Toast.makeText(root.context, R.string.something_is_wrong, Toast.LENGTH_SHORT).show()

                            spinner.setItemSelected(defPosition)
                        }

                        spinner.isEnabled = true
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return participants.size
    }

    class ViewHolder(val binding: RvParticipantItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
