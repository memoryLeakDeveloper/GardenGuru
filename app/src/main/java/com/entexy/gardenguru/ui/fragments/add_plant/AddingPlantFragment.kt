package com.entexy.gardenguru.ui.fragments.add_plant

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.databinding.FragmentAddingPlantBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment.UpdateLayoutHeightCallback
import com.entexy.gardenguru.ui.fragments.add_plant.client.ClientPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment
import com.entexy.gardenguru.utils.setString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class AddingPlantFragment : BaseFragment<FragmentAddingPlantBinding>() {

    private lateinit var pagerAdapter: PagerAdapter
    private val viewModel: AddingPlantViewModel by viewModels()

    fun interface UpdateLayoutHeightCallback {
        fun update()
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
            initSpinner(viewModel.loadGardensNames().map { it.name })
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
            page.translationX = -convertDpToPx(50F) * position
            page.scaleY = 1 - (0.01F * kotlin.math.abs(position))
        }
        binding.viewPager.apply {
            pagerAdapter = PagerAdapter(this@AddingPlantFragment, listOf())
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

    private fun convertDpToPx(dp: Float) = (dp * requireContext().resources.displayMetrics.density).toInt()

    private inner class PagerAdapter(fragment: Fragment, private val listData: List<PlantData>) : FragmentStateAdapter(fragment) {

        private val fragments = hashMapOf<Int, Fragment>()

        init {
            val data = PlantData(
                "id",
                "НЕЗАБУДКА",
                "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
                CareComplexity.Easy,
                "НЕЗАБУДКА DESC",
                SunRelation.DirectLight,
                null,
                null,
                arrayListOf(BenefitData("qweqweqweqweqwe", "qwpoqfwepofqmvw")),
                "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
                Date(),
                3,
                4,
                5,
                6,
            )
            for (i in 0 until itemCount - 1) {
                fragments[i] = PlantDescriptionFragment(data, updateLayoutHeightCallback)
            }
            fragments[itemCount - 1] = ClientPlantFragment(updateLayoutHeightCallback)
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]!!
        }

        fun getCurrentPlantNameAndData(position: Int): PlantData? {
            return (fragments[position] as GetPlantInfo).getPlantInfo()
        }

        override fun getItemCount() = 5
    }
}


class HorizontalMarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int = (30F * context.resources.displayMetrics.density).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }
}