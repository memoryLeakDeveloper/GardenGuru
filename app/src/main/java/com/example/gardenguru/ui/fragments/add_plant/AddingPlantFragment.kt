package com.example.gardenguru.ui.fragments.add_plant

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.gardenguru.R
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.FragmentAddingPlantBinding
import com.example.gardenguru.ui.fragments.add_plant.AddingPlantFragment.UpdateLayoutHeightCallback
import com.example.gardenguru.ui.fragments.add_plant.client.ClientPlantFragment
import com.example.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment
import com.example.gardenguru.utils.Extensions.setString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AddingPlantFragment : Fragment() {

    private lateinit var binding: FragmentAddingPlantBinding

    private lateinit var viewModel: AddingPlantViewModel

    private lateinit var pagerAdapter: AddingPlantFragment.PagerAdapter

    @Inject
    lateinit var viewModelFactory: AddingPlantViewModel.Factory

    fun interface UpdateLayoutHeightCallback {
        fun update()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[AddingPlantViewModel::class.java]
        binding = FragmentAddingPlantBinding.inflate(inflater, container, false)
        return binding.root
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


        CoroutineScope(Dispatchers.IO).launch {
            val gardenNames = viewModel.loadGardensNames().map { it.name }
            withContext(Dispatchers.Main) {
                initSpinner(gardenNames)
            }
        }


        setViewPager()
    }

    private fun initSpinner(gardens: List<String>) {

        with(binding) {
            spinner.initView("Введите сад", null, ArrayList(gardens), true)

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
            addItemDecoration(_root_ide_package_.com.example.gardenguru.ui.fragments.add_plant.HorizontalMarginItemDecoration(requireContext()))
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

    private inner class PagerAdapter(fragment: Fragment, private val listData: List<PlantData>) :
        FragmentStateAdapter(fragment) {

        private val fragments = hashMapOf<Int, Fragment>()

        init {
            val data = PlantData(
                "12121",
                2,
                "НЕЗАБУДКА",
                null,
                "УУУУУУУУ очень много букв УУУУУУУУь букв УУУУУУУУь много букв букв УУУУУУУУь много букв много букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверить",
                PhotoData("1", "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg", ""),
                SunRelationData(1, "22222"),
                arrayListOf(PestData("1", "EFKO"), PestData("2", "QA"), PestData("133", "YYYYY")),
                arrayListOf(ReproductionData(1, "TTTTT")),
                arrayListOf(
                    BenefitData(
                        1,
                        "rrrrrhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhr"
                    )
                ),
                "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ПОЛИТЬ ОБЯЗАТЕЛЬНО",
                "LFDFQ DMFMSDFSKMFSDMKFSDMKFSDFDSKMFSDMK",
                2,
                3,
                4,
                5,
                6,
                6,
                7,
                10,
                8,
                8,
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