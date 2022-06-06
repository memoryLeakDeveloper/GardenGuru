package com.example.gardenguru.ui.my_plants.garden_managment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.databinding.DialogRemoveGardenBinding
import com.example.gardenguru.databinding.FragmentGardenManagmentBinding
import com.example.gardenguru.ui.customview.DialogHelper
import com.example.gardenguru.ui.my_plants.MyPlantsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GardenManagementFragment : Fragment() {

    private lateinit var binding: FragmentGardenManagmentBinding
    private lateinit var viewModel: GardenManagementViewModel

    @Inject
    lateinit var viewModelFactory: GardenManagementViewModel.Factory

    private lateinit var garden: GardenData

    companion object {
        const val GARDEN_EXTRA = "garden_extra"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        garden = requireArguments().getParcelable(GARDEN_EXTRA)!!

        viewModel = ViewModelProvider(this, viewModelFactory)[GardenManagementViewModel::class.java]
        binding = FragmentGardenManagmentBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        root.resources.getString(R.string.dialog_want_to_delete_garden, garden.name, garden.getGuruEmail())

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
            val defPosition = when (garden.summerClimateSeason) {
                GardenData.SummerClimateSeason.JuneAugust -> 0
                GardenData.SummerClimateSeason.DecemberFebruary -> 1
                else -> 0
            }
            spinnerSeason.initView(null, defPosition, seasons)

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
                        if (!isSuccess){
                            Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                        }

                        dialogHelper.hideDialog()
                        btSaveGarden.isEnabled = true
                    }
                }
            }

            initParticipantList()
        }
    }

    private fun initParticipantList() {
        with(binding) {
            rvUsers.layoutManager = LinearLayoutManager(requireContext())
            rvUsers.adapter = ParticipantRecyclerAdapter(resources, garden.participants, viewModel)
        }
    }
}
