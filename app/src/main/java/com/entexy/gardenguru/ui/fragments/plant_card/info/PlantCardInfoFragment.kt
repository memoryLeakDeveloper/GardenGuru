package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.DialogPlantMovingBinding
import com.entexy.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.entexy.gardenguru.ui.PlantMockData
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.showSnackBar
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
//        val idPlant = requireArguments().getString(keyIdPlant)!!
        lifecycleScope.launch(Dispatchers.Main) {
            val data = CloudResponse.Success(PlantMockData.plant)
//            val data = viewModel.fetchPlant(idPlant) //todo
            Log.d("bugger", "data = ${data}")
            if (data is CloudResponse.Success) {
                initView(data.result)
            } else {
                Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun initView(data: PlantData) {
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name ?: data.variety
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


            buttonDelete.setOnClickListener {
                lifecycleScope.launch {
                    val dialogHelper = DialogHelper()
                    viewModel.deletePlant(data.id).collect {
                        it.getResult(
                            success = {
                                dialogHelper.hideDialog()
                                requireActivity().onBackPressed()
                            },
                            failure = {
                                dialogHelper.hideDialog()
                                root.showSnackBar(R.string.error_deleting)
                            },
                            loading = {
                                dialogHelper.showDialog(ProgressBar(requireContext()), false)
                            }
                        )
                    }
                }
            }

            buttonMove.setOnClickListener {
                lifecycleScope.launch {
                    val dialogHelper = DialogHelper()
                    viewModel.getGardens().collect { response ->
                        response.getResult(
                            success = {
                                dialogHelper.hideDialog()
                                initSelectGardenToMove(data.id, it.result)
                            },
                            failure = {
                                dialogHelper.hideDialog()
                                root.showSnackBar(R.string.error_loading_data)
                            },
                            loading = {
                                dialogHelper.showDialog(ProgressBar(requireContext()), false)
                            }
                        )
                    }

                }

            }

            etPlantName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val dialogHelper = DialogHelper()
                        viewModel.setPlantName(data.id, etPlantName.text.toString()).collect {
                            it.getResult(
                                success = {
                                    dialogHelper.hideDialog()
                                },
                                failure = {
                                    etPlantName.setText(data.name)
                                    dialogHelper.hideDialog()
                                    root.showSnackBar(R.string.error_update_data)
                                },
                                loading = {
                                    dialogHelper.showDialog(ProgressBar(requireContext()), false)
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
            lifecycleScope.launch {
                val dialogHelper = DialogHelper()
                viewModel.movePlant(plantId, gardens[position].id).collect {
                    it.getResult(
                        success = {
                            dialogHelper.hideDialog()
                        },
                        failure = {
                            dialogHelper.hideDialog()
                            requireView().showSnackBar(R.string.error_update_data)
                        },
                        loading = {
                            dialogHelper.showDialog(ProgressBar(requireContext()), false)
                        })
                }
            }
        }
        dialog.showDialog(bindingDialog.root)
    }
}
