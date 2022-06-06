package com.example.gardenguru.ui.add_plant

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.gardenguru.R
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.FragmentAddingPlantBinding
import com.example.gardenguru.ui.add_plant.client.ClientPlantFragment
import com.example.gardenguru.ui.add_plant.description.PlantDescriptionFragment
import com.example.gardenguru.utils.Extensions.setString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddingPlantFragment : Fragment() {

    private lateinit var binding: FragmentAddingPlantBinding

    private lateinit var viewModel: AddingPlantViewModel

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
        with(binding){
            header.title.setString(R.string.adding)
            header.back.setOnClickListener {
                requireActivity().onBackPressed()
            }

            buttonAdd.setOnClickListener{
//                viewPager.adapter!!.item
            }
        }


        CoroutineScope(Dispatchers.IO).launch {
            val gardenNames = viewModel.loadGardensNames().map { it.name }

            launch(Dispatchers.Main) {
                initSpinner(gardenNames)
            }
        }


        setViewPager()
    }

    private fun initSpinner(gardens: List<String>){

        with(binding){
            spinner.initView("Введите сад", null, ArrayList(gardens), true)

            spinner.setValueListener{ pos, _ ->
                viewModel.selectedGarden = pos
            }

            CoroutineScope(Dispatchers.IO).launch {
                val garden = viewModel.createGarden("fff")

                if (garden == null){
                    launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), R.string.error_when_create_garden, Toast.LENGTH_SHORT)
                        spinner
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
            adapter = PagerAdapter(this@AddingPlantFragment, listOf())
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

        override fun getItemCount() = 5

        override fun createFragment(position: Int): Fragment {
            val data = PlantData(
                "12121",
                2,
                "НЕЗАБУДКА",
                "УУУУУУУУ очень много букв УУУУУУУУь букв УУУУУУУУь много букв букв УУУУУУУУь много букв много букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверить",
                PhotoData("1", "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg", ""),
                SunRelationData(1, "22222"),
                arrayListOf(PestData("1", "EFKO", Uri.EMPTY), PestData("2", "QA", Uri.EMPTY), PestData("133", "YYYYY", Uri.EMPTY)),
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
                8
            )
            return if (position != 4) PlantDescriptionFragment(data, updateLayoutHeightCallback) else ClientPlantFragment(
                updateLayoutHeightCallback
            )
        }
    }

    fun getCurrentPlantData(){

    }

    class HorizontalMarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = (30F * context.resources.displayMetrics.density).toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

}