package com.entexy.gardenguru.ui.fragments.add_plant

import AddingPlantPagerAdapter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.data.plant.search.mapToPlantData
import com.entexy.gardenguru.databinding.FragmentAddingPlantBinding
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment.Companion.PLANT_DATA_KEY
import com.entexy.gardenguru.utils.setString
import com.entexy.gardenguru.utils.showToastLong
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddingPlantFragment : BaseFragment<FragmentAddingPlantBinding>() {

    private lateinit var pagerAdapter: AddingPlantPagerAdapter
    private val viewModel: AddingPlantViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.setString(R.string.adding)
        setListeners()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            val plantSearchByNameQuire = requireArguments().getString(SEARCH_BY_VARIETY_ARGUMENTS_KEY)
            if (plantSearchByNameQuire != null) {
                val response = viewModel.findPlantsByVariety(plantSearchByNameQuire)
                if (response is CloudResponse.Success)
                    setViewPager(response.result)
            } else {
                val plantSearchQuires = requireArguments().getStringArrayList(SEARCH_ARGUMENTS_KEY)!!
                val response = viewModel.findPlants(plantSearchQuires.toList())
                if (response is CloudResponse.Success)
                    setViewPager(response.result)

            }
        }
    }

    private fun setListeners() = binding.apply {
        header.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnAdd.setOnClickListener {
            val plantData = pagerAdapter.getCurrentPlantNameAndData(viewPager.currentItem)
            if (plantData != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val response = viewModel.addPlant(plantData.mapToPlantData()!!)
                    response.collect {
                        withContext(Dispatchers.Main) {
                            it.getResult(
                                loading = {
                                    requireContext().showToastLong("LOOOOOODING")
                                },
                                success = {
                                    findNavController().navigate(R.id.action_addingPlantFragment_to_myPlantsFragment)
                                },
                                failure = {
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
