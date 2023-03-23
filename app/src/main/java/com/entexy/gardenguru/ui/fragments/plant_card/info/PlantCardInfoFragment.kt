package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.DialogPlantMovingBinding
import com.entexy.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.setImageByGlide
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlantCardInfoFragment : BaseFragment<FragmentPlantCardInfoBinding>() {

    private val viewModel: PlantCardInfoViewModel by viewModels()
    private val bindingDialog by lazy { DialogPlantMovingBinding.inflate(LayoutInflater.from(requireContext())) }
    private val dialog by lazy { DialogHelper() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPlant = requireArguments().getString(keyIdPlant)!!
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.fetchPlant(idPlant).collect { response ->
                response.getResult(
                    success = {
                        iniView(it.result)
                    },
                    failure = {
                        binding.scroll.toGone()
                        binding.cardNoMatches.toVisible()
                    },
                    loading = {
                        //todo
                    }
                )

            }
        }
    }

    private fun iniView(data: PlantData) = binding.apply {
        plantPhoto.setImageByGlide(data.photo)
        plantName.text = data.name
        data.description?.let {
            plantInfo.initView(it, null)
        } ?: run {
            plantInfo.toGone()
            aboutPlant.toGone()
            plantName1.toGone()
        }
        careDifficult.initView(data.careComplexity, true)
        wheather.initView(data)
        careDescription.initView(data)
        pests.initView(data.pests)
        benefits.initView(data.benefits)
        buttonMove.setOnClickListener {
            bindingDialog.spinner.initView(
                "Введите сад",
                arrayListOf("11111111111", "2222222", "33333", "444444", "5555555"),
                true
            )
            dialog.showDialog(bindingDialog.root)
        }
    }

    companion object {
        private const val keyIdPlant = "PLANT_ID"
    }
}