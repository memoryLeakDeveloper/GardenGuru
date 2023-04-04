package com.entexy.gardenguru.ui.fragments.plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.databinding.FragmentMyPlantsBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.utils.showSnackBar
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPlantsFragment : BaseFragment<FragmentMyPlantsBinding>() {

    private val viewModel: MyPlantsViewModel by viewModels()
    private lateinit var rvAdapter: PlantsRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGardenList()
        initNoPlants()
        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.my_plants)
            }
        }
    }

    private fun initNoPlants() {
        with(binding) {
            ivCam.setOnClickListener {
                findNavController().navigate(R.id.action_myPlantsFragment_to_cameraFragment)
            }
            etSearchPlant.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    findNavController().navigate(
                        R.id.action_myPlantsFragment_to_addingPlantFragment, bundleOf(
                            AddingPlantFragment.SEARCH_BY_VARIETY_ARGUMENTS_KEY to etSearchPlant.text.toString()
                        )
                    )
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initGardenList() {
        with(binding) {
            rvAdapter = PlantsRecyclerAdapter()
            rvPlants.layoutManager = LinearLayoutManager(requireContext())
            rvPlants.adapter = rvAdapter
        }

        val dialogHelper = DialogHelper()
        lifecycleScope.launch {
            viewModel.fetchPlants().collect { cloudResponse ->
                cloudResponse.getResult(success = {
                    dialogHelper.hideDialog()
                    if (it.result.isEmpty()) {
                        binding.rvPlants.toGone()
                        binding.noPlantsContainer.toVisible()
                    } else {
                        rvAdapter.setData(ArrayList(it.result))
                        binding.rvPlants.toVisible()
                        binding.noPlantsContainer.toGone()
                    }
                }, failure = {
                    dialogHelper.hideDialog()
                    requireView().showSnackBar(R.string.error_loading_data)
                }, loading = {
                    dialogHelper.showDialog(ProgressBar(requireContext()))
                })
            }
        }
    }
}
