package com.example.gardenguru.ui.add.plant

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
import com.example.gardenguru.databinding.AddingPlantFragmentBinding
import com.example.gardenguru.ui.add.plant.description.PlantDescriptionFragment
import com.example.gardenguru.utils.Extensions.setString

class AddingPlantFragment : Fragment() {

    private lateinit var binding: AddingPlantFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AddingPlantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.setString(R.string.adding)
        binding.spinner.initView("DDDDDDDDDDDDDDDDDDDD", arrayListOf("11111111111", "2222222", "2222222", "2222222", "2222222"), true)
        binding.viewPager.adapter = PagerAdapter(this@AddingPlantFragment, 3)
        binding.viewPager.offscreenPageLimit = 1
        val nextItemVisiblePx = convertDpToPx(20F)
        val currentItemHorizontalMarginPx = convertDpToPx(30F)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.01F * kotlin.math.abs(position))
        }
        binding.viewPager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(requireContext())
        binding.viewPager.addItemDecoration(itemDecoration)
    }

    class HorizontalMarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = (30F * context.resources.displayMetrics.density).toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

    private fun convertDpToPx(dp: Float) = (dp * requireContext().resources.displayMetrics.density).toInt()

    private inner class PagerAdapter(fragment: Fragment, private val itemsCount: Int) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = 3

        override fun createFragment(position: Int) =
            when (position) {
                0 -> PlantDescriptionFragment()
                1 -> PlantDescriptionFragment()
                2 -> PlantDescriptionFragment()
                else -> PlantDescriptionFragment()
            }
    }

}