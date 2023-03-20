package com.example.gardenguru.ui.fragments.plant_card.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.DialogPlantMovingBinding
import com.example.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlantCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardInfoBinding
    private val viewModel: PlantCardInfoViewModel by viewModels()
    private val bindingDialog by lazy { DialogPlantMovingBinding.inflate(LayoutInflater.from(requireContext())) }
    private val dialog by lazy { DialogHelper() }
    private val keyIdPlant = "PLANT_ID"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlantCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPlant = requireArguments().getString(keyIdPlant)!!
        lifecycleScope.launch(Dispatchers.Main) {
            val data = viewModel.fetchPlant(idPlant)
            Log.d("bugger", "data = ${data}")
            if (data == null) {
                Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else
                iniView(data)
        }
    }

    private fun iniView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo.file)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name
            data.description?.let {
                plantInfo.initView(it, null)
            } ?: run {
                plantInfo.visibility = View.GONE
                aboutPlant.visibility = View.GONE
                plantName1.visibility = View.GONE
            }
            careDifficult.initView(data.careComplexity, true)
            wheather.initView(data)
            careDescription.initView(data)
            pests.initView(data.pests)
            benefits.initView(data.benefits)
            buttonMove.setOnClickListener {
                bindingDialog.spinner.initView(
                    "Введите сад",
                    null,
                    arrayListOf("11111111111", "2222222", "33333", "444444", "5555555"),
                    true
                )
                dialog.showDialog(bindingDialog.root)
            }
        }
    }
}
