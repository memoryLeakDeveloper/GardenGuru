package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.DialogPlantMovingBinding
import com.entexy.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.entexy.gardenguru.ui.PlantMockData
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.setImageByGlide
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import com.entexy.gardenguru.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PlantCardInfoFragment : BaseFragment<FragmentPlantCardInfoBinding>() {

    private val viewModel: PlantCardInfoViewModel by viewModels()
    private val bindingDialog by lazy { DialogPlantMovingBinding.inflate(LayoutInflater.from(requireContext())) }
    private val dialog by lazy { DialogHelper() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val idPlant = requireArguments().getString(keyIdPlant)!!
//        lifecycleScope.launch(Dispatchers.Main) {
//            viewModel.fetchPlant(idPlant).collect { response ->
//                response.getResult(
//                    success = {
//                        iniView(it.result)
//                    },
//                    failure = {
//                        binding.scroll.toGone()
//                        binding.cardNoMatches.toVisible()
//                    },
//                    loading = {
//                        //todo
//                    }
//                )
//
//            }
        }
//    }

    private fun initView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name ?: data.variety
            data.description?.let {
                plantInfo.initView(it)
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


            buttonDelete.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.deletePlant(data.id).collect {
                        it.getResult(
                            success = {
                                requireActivity().onBackPressed()
                            },
                            failure = {
                                root.showSnackBar(R.string.error_deleting)
                            },
                            loading = {
                                TODO()
                            }
                        )
                    }
                }
            }

            buttonMove.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.getGardens().collect { response ->
                        response.getResult(
                            success = {
                                withContext(Dispatchers.Main) {
                                    initSelectGardenToMove(data.id, it.result)
                                }
                            },
                            failure = {
                                root.showSnackBar(R.string.error_loading_data)
                            },
                            loading = {
                                TODO()
                            }
                        )
                    }

                }

            }

            etPlantName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.setPlantName(data.id, etPlantName.text.toString()).collect {
                            it.getResult(
                                success = {
                                    TODO()
                                },
                                failure = {
                                    root.showSnackBar(R.string.error_update_data)
                                },
                                loading = {
                                    TODO()
                                }
                            )
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun initSelectGardenToMove(plantId: String, gardens: List<GardenData>) {
        bindingDialog.spinner.initView(
            null,
            ArrayList(gardens.map { it.name }),
            true
        )
        bindingDialog.spinner.setValueListener { position: Int, name: String ->
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.movePlant(plantId, gardens[position].id).collect {
                    it.getResult(
                        success = {
                            TODO()
                        },
                        failure = {
                            requireView().showSnackBar(R.string.error_update_data)
                        },
                        loading = {
                            TODO()
                        })
                }
            }
        }
        dialog.showDialog(bindingDialog.root)
    }

}