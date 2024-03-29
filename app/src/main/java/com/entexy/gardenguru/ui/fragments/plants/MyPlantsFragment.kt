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
import com.entexy.gardenguru.data.plant.mapToPlantCloud
import com.entexy.gardenguru.databinding.FragmentMyPlantsBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.utils.bugger
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
    }

    private fun initNoPlants() = binding.apply {
        header.title.setText(R.string.my_plants)
        header.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initGardenList() = binding.apply {
        rvAdapter = PlantsRecyclerAdapter()
        rvPlants.layoutManager = LinearLayoutManager(requireContext())
        rvPlants.adapter = rvAdapter
        val dialogHelper = DialogHelper()
        lifecycleScope.launch {
            viewModel.fetchPlants().collect { cloudResponse ->
                cloudResponse.getResult(success = {
                    dialogHelper.hideDialog()
                    if (it.result.isEmpty()) {
                        rvPlants.toGone()
                        noPlantsContainer.toVisible()
                    } else {
                        rvAdapter.setData(it.result)
                        rvPlants.toVisible()
                        noPlantsContainer.toGone()
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
