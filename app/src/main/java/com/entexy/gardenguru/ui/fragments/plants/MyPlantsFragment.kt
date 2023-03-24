package com.entexy.gardenguru.ui.fragments.plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.databinding.FragmentMyPlantsBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPlantsFragment : BaseFragment<FragmentMyPlantsBinding>() {

    private val viewModel: MyPlantsViewModel by viewModels()
    private lateinit var rvAdapter: GardensRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.my_plants)
            }

            initGardenList()

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getGardens().collect { cloudResponse ->
                    cloudResponse.getResult(
                        success = {
                            binding.progressBar.visibility = View.GONE

                            if (it.result.isEmpty()) {
                                rvGardens.visibility = View.GONE
                                noPlantsContainer.visibility = View.VISIBLE
                                //search logic
                            } else {
                                rvAdapter.setData(ArrayList(it.result))
                                rvGardens.visibility = View.VISIBLE
                                noPlantsContainer.visibility = View.GONE
                            }
                        },
                        failure = {
                            binding.progressBar.visibility = View.GONE
                            //TODO show error message
                        },
                        loading = {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    )
                }
            }

        }
    }

    private fun initNoPlants() {
        with(binding) {
            ivCam.setOnClickListener {
                findNavController().navigate(R.id.action_myPlantsFragment_to_cameraFragment)
            }
            etSearchPlant.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    findNavController().navigate(
                        R.id.action_myPlantsFragment_to_addingPlantFragment,
                        bundleOf(
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
            rvAdapter = GardensRecyclerAdapter()
            rvGardens.layoutManager = LinearLayoutManager(requireContext())
            rvGardens.adapter = rvAdapter
        }
    }
}
