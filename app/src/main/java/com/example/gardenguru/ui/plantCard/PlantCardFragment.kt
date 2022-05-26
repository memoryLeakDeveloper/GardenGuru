package com.example.gardenguru.ui.plantCard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.DialogRemoveGardenBinding
import com.example.gardenguru.databinding.FragmentGardenManagmentBinding
import com.example.gardenguru.databinding.FragmentPlantCardBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardBinding
    private var viewModel = PlantCardViewModel()//: PlantCardViewModel by viewModels()  todo

    private lateinit var plantData: PlantData


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        plantData = PlantData(
            "0",
            0,
            "Иван",
            "Кактус",
            arrayListOf(
                PhotoData(
                    "0",
                    "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                )
            ),
            SunRelationData(0, ""),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        )//requireArguments().getParcelable(GARDEN_EXTRA)!!

        binding = FragmentPlantCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            title.text = plantData.name

            calendar.setOnClickListener {

            }

        }


    }
}
