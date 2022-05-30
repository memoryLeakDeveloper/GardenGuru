package com.example.gardenguru.ui.add_plant

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.gardenguru.R
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.FragmentAddingPlantBinding
import com.example.gardenguru.ui.add_plant.client.ClientPlantFragment
import com.example.gardenguru.ui.add_plant.description.PlantDescriptionFragment
import com.example.gardenguru.ui.add_plant.AddingPlantFragment.ClickCallback
import com.example.gardenguru.utils.Extensions.setString

class AddingPlantFragment : Fragment() {

    private lateinit var binding: FragmentAddingPlantBinding
    private val clickCallback = ClickCallback { updateViewPagerHeight() }
    private val viewPagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateViewPagerHeight()
            binding.scroll.smoothScrollTo(0, 0)
        }
    }

    fun interface ClickCallback {
        fun click()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddingPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.setString(R.string.adding)
        binding.spinner.initView("Введите сад", null, arrayListOf("11111111111", "2222222", "33333", "444444", "5555555"), true)
        setViewPager()
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
                "СУЧья НЕЗАБУДКА",
                "УУУУУУУУ очень много букв УУУУУУУУь букв УУУУУУУУь много букв букв УУУУУУУУь много букв много букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверитьмного букв. надо здесь написать чтобы было, надо. текст проверить",
                arrayListOf(PhotoData("1", "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg")),
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
                8
            )
            return if (position != 4) PlantDescriptionFragment(data, clickCallback) else ClientPlantFragment(clickCallback)
        }
    }

    class HorizontalMarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = (30F * context.resources.displayMetrics.density).toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

}