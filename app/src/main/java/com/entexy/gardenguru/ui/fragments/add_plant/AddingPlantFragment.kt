package com.entexy.gardenguru.ui.fragments.add_plant

import AddingPlantPagerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentAddingPlantBinding
import com.entexy.gardenguru.utils.setString
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

//        val plantSearchByNameQuire = requireArguments().getString(SEARCH_BY_NAME_ARGUMENTS_KEY)
//        lifecycleScope.launch {
//            if (plantSearchByNameQuire != null) {
//                viewModel.findPlantsByVariety(plantSearchByNameQuire)
//            } else {
//                val plantSearchQuires = requireArguments().getStringArray(SEARCH_ARGUMENTS_KEY)!!
//                viewModel.findPlants(plantSearchQuires.toList())
//            }
//        } //todo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInsets(binding.scrollRoot)
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
        setViewPager()
    }

    private fun setViewPager() {
        binding.viewPager.apply {
            pagerAdapter = AddingPlantPagerAdapter(this@AddingPlantFragment, listOf())
            adapter = pagerAdapter
            offscreenPageLimit = 2
            binding.dotsIndicator.attachTo(this)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    if()
                }
            })
        }
    }

    companion object {
        const val SEARCH_ARGUMENTS_KEY = "search-arguments-key"
        const val SEARCH_BY_VARIETY_ARGUMENTS_KEY = "search-by-name-arguments-key"
    }
}
