package com.entexy.gardenguru.ui.fragments.plants.garden_managment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.databinding.DialogRemoveGardenBinding
import com.entexy.gardenguru.databinding.FragmentGardenManagmentBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GardenManagementFragment : BaseFragment<FragmentGardenManagmentBinding>() {

    private val viewModel: GardenManagementViewModel by viewModels()

    private lateinit var garden: GardenData

    companion object {
        const val GARDEN_EXTRA = "garden_extra"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        garden = requireArguments().getParcelable(GARDEN_EXTRA)!!

        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.garden_management)
            }

            etGardenName.setText(garden.name)

            btRemoveGarden.setOnClickListener {
                val dialogHelper = DialogHelper()
                val dialogBinding = DialogRemoveGardenBinding.inflate(LayoutInflater.from(root.context))
                with(dialogBinding) {

                    tvDialogDescription.text =
                        root.resources.getString(
                            R.string.dialog_want_to_delete_garden,
                            garden.name,
                        )

                    btNo.setOnClickListener {
                        dialogHelper.hideDialog()
                    }
                    btYes.setOnClickListener {
                        dialogHelper.hideDialog()
                        //delete
                    }
                }
                dialogHelper.showDialog(dialogBinding.root)
            }

            val seasons = arrayListOf(
                resources.getString(GardenData.SummerClimateSeason.JuneAugust.stringNameRes),
                resources.getString(GardenData.SummerClimateSeason.DecemberFebruary.stringNameRes)
            )
            spinnerSeason.initView(null, seasons)

            btSaveGarden.setOnClickListener {
                btSaveGarden.isEnabled = false

                val dialogHelper = DialogHelper()

                val progressView = ProgressBar(requireContext())
                dialogHelper.showDialog(progressView, false)

                CoroutineScope(Dispatchers.IO).launch {
                    val season = GardenData.getSummerSeasonByStringValue(resources, spinnerSeason.spinnerValue!!)
                    val isSuccess =
                        viewModel.editGarden(garden.id, etGardenName.text.toString(), season.value)

                    launch(Dispatchers.Main) {
                        if (!isSuccess) {
                            Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                        }

                        dialogHelper.hideDialog()
                        btSaveGarden.isEnabled = true
                    }
                }
            }

            btRemoveGarden.setOnClickListener {
                val dialogHelper = DialogHelper()
                val dialogBinding = DialogRemoveGardenBinding.inflate(LayoutInflater.from(root.context))
                with(dialogBinding) {

                    tvDialogDescription.text = root.resources.getString(
                        R.string.dialog_want_to_delete_garden,
                        garden.name
                    )

                    btNo.setOnClickListener {
                        dialogHelper.hideDialog()
                    }
                    btYes.setOnClickListener {
                        dialogHelper.hideDialog()

                        btRemoveGarden.isEnabled = false

                        val progressView = ProgressBar(requireContext())
                        dialogHelper.showDialog(progressView, false)

                        lifecycleScope.launch {
                            val isSuccess = viewModel.deleteGarden(garden.id)
                            dialogHelper.hideDialog()

                            btRemoveGarden.isEnabled = true
                            if (isSuccess) {
                                requireActivity().onBackPressed()
                            } else Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                dialogHelper.showDialog(dialogBinding.root)

            }

            initParticipantList()
        }
    }

    private fun initParticipantList() {
        with(binding) {

            etNewParticipantEmail.addTextChangedListener {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                    addParticipantContainer.setBackgroundResource(R.drawable.button_green_stroke)
                } else {
                    addParticipantContainer.setBackgroundResource(R.drawable.bg_edit_text_error)
                }
            }

            btAddParticipant.setOnClickListener {
                val email = etNewParticipantEmail.text
                if (!email.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
                    lifecycleScope.launch {
                        val isSuccess = viewModel.addNewParticipant(etNewParticipantEmail.text.toString(), garden.id)

                        Toast.makeText(requireContext(), "participant added: $isSuccess", Toast.LENGTH_SHORT).show()
                        if (!isSuccess) {
                            // TODO
                        } else {
                            etNewParticipantEmail.text?.clear()
                        }
                    }
                } else {
                    addParticipantContainer.setBackgroundResource(R.drawable.bg_edit_text_error)
                }
            }

            rvUsers.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
