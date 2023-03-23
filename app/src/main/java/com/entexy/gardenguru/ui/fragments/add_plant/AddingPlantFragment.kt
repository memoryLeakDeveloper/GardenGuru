package com.entexy.gardenguru.ui.fragments.add_plant

import AddingPlantPagerAdapter
import HorizontalMarginItemDecoration
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
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment.UpdateLayoutHeightCallback
import com.entexy.gardenguru.utils.convertDpToPx
import com.entexy.gardenguru.utils.setString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddingPlantFragment : BaseFragment<FragmentAddingPlantBinding>() {

    private lateinit var pagerAdapter: AddingPlantPagerAdapter
    private val viewModel: AddingPlantViewModel by viewModels()

    fun interface UpdateLayoutHeightCallback {
        fun update()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val plantSearchQuires = requireArguments().getStringArray(SEARCH_ARGUMENTS_KEY) //todo
//        viewModel.findPlants(plantSearchQuires) //todo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            header.title.setString(R.string.adding)
            header.back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            buttonAdd.setOnClickListener {
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
//            initSpinner(viewModel.loadGardensNames().map { it.name }) //todo
        }
        setViewPager()
    }

    private fun initSpinner(gardens: List<String>) {

        with(binding) {
            spinner.initView("Введите сад", ArrayList(gardens), true)

            spinner.setValueListener { pos, _ ->
                viewModel.selectedGarden = pos
            }

            spinner.setNewItemListener { pos: Int, text: String ->
                CoroutineScope(Dispatchers.IO).launch {
                    val garden = viewModel.createGarden(text)

                    if (garden == null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), R.string.error_when_create_garden, Toast.LENGTH_SHORT)
                                .show()
                            spinner.deleteLastItem()
                        }
                        viewModel.selectedGarden = -1
                    } else {
                        viewModel.selectedGarden = pos
                    }
                }
            }

        }
    }

    private fun setViewPager() {
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -requireContext().convertDpToPx(50F) * position
            page.scaleY = 1 - (0.01F * kotlin.math.abs(position))
        }
        binding.viewPager.apply {
            pagerAdapter = AddingPlantPagerAdapter(this@AddingPlantFragment, listOf(), updateLayoutHeightCallback)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            setPageTransformer(pageTransformer)
            addItemDecoration(HorizontalMarginItemDecoration(requireContext()))
        }
        binding.viewPager.registerOnPageChangeCallback(viewPagerListener)
    }

    private val updateLayoutHeightCallback = UpdateLayoutHeightCallback { updateViewPagerHeight() }
    private val viewPagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateViewPagerHeight()
            binding.scroll.smoothScrollTo(0, 0)
        }
    }

    fun updateViewPagerHeight() {
        val position = binding.viewPager.currentItem
        if (childFragmentManager.fragments.size > position) {
            childFragmentManager.fragments[position].view?.let {
                updatePagerHeightForChild(it, binding.viewPager)
            }
        }
    }

    private fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
        view.post {
            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(wMeasureSpec, hMeasureSpec)
            if (pager.layoutParams.height != view.measuredHeight) {
                pager.layoutParams = (pager.layoutParams)
                    .also { lp ->
                        lp.height = view.measuredHeight
                    }
            }
        }
    }

    companion object {
        const val SEARCH_ARGUMENTS_KEY = "search-arguments-key"
    }
}
