package com.example.gardenguru.ui.garden_managment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.data.garden.GardenData
import com.example.gardenguru.data.garden.GardenSimple
import com.example.gardenguru.databinding.DialogLeaveGardenBinding
import com.example.gardenguru.databinding.DialogRemoveGardenBinding
import com.example.gardenguru.databinding.FragmentGardenManagmentBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GardenManagementFragment : Fragment() {

    private lateinit var binding: FragmentGardenManagmentBinding
    private var viewModel = GardenManagementViewModel()//: MyPlantsViewModel by viewModels()  todo

    private lateinit var garden: GardenSimple

    companion object{
        const val GARDEN_EXTRA = "garden_extra"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        garden = requireArguments().getParcelable(GARDEN_EXTRA)!!

        binding = FragmentGardenManagmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            header.apply {
                back.setOnClickListener{
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.garden_management)
            }

            etGardenName.setText(garden.name)

            btRemoveGarden.setOnClickListener{
                val dialogHelper = DialogHelper()
                val dialogBinding = DialogLeaveGardenBinding.inflate(LayoutInflater.from(root.context))
                with(dialogBinding){

                    tvDialogDescription.text = root.resources.getString(R.string.dialog_want_to_delete_garden, garden.name, garden.gardenOwner)

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
    private fun initGardenList() {
        with(binding) {

        }
    }
}
