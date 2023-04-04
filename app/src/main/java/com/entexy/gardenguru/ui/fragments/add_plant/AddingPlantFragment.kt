package com.entexy.gardenguru.ui.fragments.add_plant

import AddingPlantPagerAdapter
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.data.plant.search.mapToPlantData
import com.entexy.gardenguru.databinding.FragmentAddingPlantBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment.Companion.PLANT_DATA_KEY
import com.entexy.gardenguru.utils.setString
import com.entexy.gardenguru.utils.showToastLong
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddingPlantFragment : BaseFragment<FragmentAddingPlantBinding>() {

    private lateinit var pagerAdapter: AddingPlantPagerAdapter
    private val viewModel: AddingPlantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
        observeLiveData()
    }

    private fun fetchData() = lifecycleScope.launch {
        val plantSearchByNameQuire = requireArguments().getString(SEARCH_BY_VARIETY_ARGUMENTS_KEY)
        plantSearchByNameQuire?.let {
            handleSearchResponse(viewModel.findPlantsByVariety(plantSearchByNameQuire))
        } ?: run {
            val plantSearchQuires = requireArguments().getStringArrayList(SEARCH_ARGUMENTS_KEY)!!
            handleSearchResponse(viewModel.findPlants(plantSearchQuires.toList()))
        }
    }

    private fun initView() = binding.apply {
        header.title.setString(R.string.adding)
        header.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setListeners() = binding.apply {
        btnAdd.setOnClickListener {
            pagerAdapter.getCurrentPlantNameAndData(viewPager.currentItem)?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.addPlant(it.mapToPlantData()).collect {
                        withContext(Dispatchers.Main) {
                            val dialog = DialogHelper()
                            it.getResult(
                                loading = {
                                    dialog.showDialog(ProgressBar(requireContext()), false)
                                },
                                success = {
                                    dialog.hideDialog()
                                    findNavController().navigate(R.id.action_addingPlantFragment_to_myPlantsFragment)
                                },
                                failure = {
                                    dialog.hideDialog()
                                    requireContext().showToastLong(R.string.something_is_wrong)
                                }
                            )
                        }
                    }
                }
            }
        }
        tvSeeMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_addingPlantFragment_to_plantDescriptionFragment,
                bundleOf(PLANT_DATA_KEY to pagerAdapter.getCurrentPlantNameAndData(viewPager.currentItem))
            )
        }
    }

    private fun observeLiveData() {
        viewModel.plantsData.observe(viewLifecycleOwner) {
            setViewPager(it)
        }
    }

    private suspend fun handleSearchResponse(response: Flow<CloudResponse<List<PlantSearchData>>>) = withContext(Dispatchers.Main) {
        val dialog = DialogHelper()
        response.collect {
            it.getResult(
                success = {
                    viewModel.plantsData.postValue(it.result)
                    dialog.hideDialog()
                },
                loading = {
                    dialog.showDialog(ProgressBar(requireContext()), false)
                },
                failure = {
                    requireContext().showToastLong(R.string.something_is_wrong)
                    dialog.hideDialog()
                }
            )
        }
    }

    private fun setViewPager(list: List<PlantSearchData>) = binding.apply {
        pagerAdapter = AddingPlantPagerAdapter(this@AddingPlantFragment, list)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 2
        dotsIndicator.attachTo(viewPager)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == list.size) {
                    btnAdd.toGone()
                    tvSeeMore.toGone()
                } else {
                    btnAdd.toVisible()
                    tvSeeMore.toVisible()
                }
            }
        })
    }

    companion object {
        const val SEARCH_ARGUMENTS_KEY = "search-arguments-key"
        const val SEARCH_BY_VARIETY_ARGUMENTS_KEY = "search-by-name-arguments-key"
    }
}
