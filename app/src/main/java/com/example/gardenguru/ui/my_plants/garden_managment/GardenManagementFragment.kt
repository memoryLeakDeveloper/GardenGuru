package com.example.gardenguru.ui.my_plants.garden_managment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.data.garden.models.GardenPlantData
import com.example.gardenguru.data.garden.models.Participant
import com.example.gardenguru.databinding.DialogRemoveGardenBinding
import com.example.gardenguru.databinding.FragmentGardenManagmentBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GardenManagementFragment : Fragment() {

    private lateinit var binding: FragmentGardenManagmentBinding
    private lateinit var viewModel: GardenManagementViewModel

    private lateinit var garden: GardenData

    companion object {
        const val GARDEN_EXTRA = "garden_extra"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        garden = GardenData(
            "",
            "",
            "Сад 1",
            arrayListOf(
                GardenPlantData(
                    "0",
                    "Иван",
                    "Кактус",
                    "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                ),
                GardenPlantData(
                    "0",
                    "Степан",
                    "Фикус",
                    "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                ), GardenPlantData(
                    "0",
                    "Женя",
                    "Кактус",
                    "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                )
            ),
            arrayListOf(
                Participant("qqq", "email1@q.q", Participant.RoleInGarden.Newbie),
                Participant("qqq", "email2@q.q", Participant.RoleInGarden.Experienced),
                Participant("qqq", "email3@q.q", Participant.RoleInGarden.Experienced),
                Participant("qqq", "email4@q.q", Participant.RoleInGarden.Guru),
            )
        )

        viewModel = ViewModelProvider(this)[GardenManagementViewModel::class.java]
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
                        root.resources.getString(R.string.dialog_want_to_delete_garden, garden.name, garden.guru)

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
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initParticipantList() {
        with(binding) {
            rvUsers.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
