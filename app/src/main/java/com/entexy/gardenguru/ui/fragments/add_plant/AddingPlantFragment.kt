package com.entexy.gardenguru.ui.fragments.add_plant

import AddingPlantPagerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentAddingPlantBinding
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment.Companion.PLANT_DATA_KEY
import com.entexy.gardenguru.utils.setString
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val plantSearchByNameQuire = requireArguments().getString(SEARCH_BY_VARIETY_ARGUMENTS_KEY)
        lifecycleScope.launch {
            if (plantSearchByNameQuire != null) {
                val response = viewModel.findPlantsByVariety(plantSearchByNameQuire)
            } else {
                val plantSearchQuires = requireArguments().getStringArrayList(SEARCH_ARGUMENTS_KEY)!!
                val response = viewModel.findPlants(plantSearchQuires.toList())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            header.title.setString(R.string.adding)
            header.back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            btnAdd.setOnClickListener {
                val plantData = pagerAdapter.getCurrentPlantNameAndData(viewPager.currentItem)
                if (plantData != null && viewModel.selectedGarden != -1) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val isSuccess = viewModel.createPlant(plantData)
                        withContext(Dispatchers.Main) {
                            if (isSuccess) {
                                findNavController().navigate(R.id.timetableFragment)//todo
                            } else {
                                Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    if (viewModel.selectedGarden == -1)
                        Toast.makeText(requireContext(), R.string.error_garden_not_selected, Toast.LENGTH_SHORT).show()
                    else Toast.makeText(requireContext(), R.string.error_not_all_data_populated, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {

        }
        setViewPager(emptyList())
        binding.tvSeeMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_addingPlantFragment_to_plantDescriptionFragment,
                bundleOf(PLANT_DATA_KEY to pagerAdapter.getCurrentPlantNameAndData(binding.viewPager.currentItem))
            )
        }
    }

    private fun setViewPager(list: List<PlantData>) = binding.apply {
        viewPager.apply {
            pagerAdapter = AddingPlantPagerAdapter(this@AddingPlantFragment, list)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            dotsIndicator.attachTo(this)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //todo list size
                    if (position == 3) {
                        btnAdd.toGone()
                        tvSeeMore.toGone()
                    } else {
                        btnAdd.toVisible()
                        tvSeeMore.toVisible()
                    }
                }
            })
        }
    }

    companion object {
        const val SEARCH_ARGUMENTS_KEY = "search-arguments-key"
        const val SEARCH_BY_VARIETY_ARGUMENTS_KEY = "search-by-name-arguments-key"
    }
}
