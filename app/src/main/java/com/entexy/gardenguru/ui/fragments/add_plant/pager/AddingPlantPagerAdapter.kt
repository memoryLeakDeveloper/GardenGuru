import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo
import com.entexy.gardenguru.ui.fragments.add_plant.result.NoMatchesSearchFragment
import com.entexy.gardenguru.ui.fragments.add_plant.result.PlantSearchResultFragment

class AddingPlantPagerAdapter(fragment: Fragment, private val listData: List<PlantSearchData>) : FragmentStateAdapter(fragment) {

    private val fragments = hashMapOf<Int, Fragment>()

    init {
        for (i in 0 until itemCount - 1) {
            fragments[i] = PlantSearchResultFragment(listData[i])
        }
        fragments[itemCount - 1] = NoMatchesSearchFragment()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]!!
    }

    fun getCurrentPlantNameAndData(position: Int): PlantSearchData? {
        return (fragments[position] as GetPlantInfo).getPlantInfo()
    }

    override fun getItemCount() = listData.size + 1
}