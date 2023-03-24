import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.ui.PlantMockData
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment

class AddingPlantPagerAdapter(
    fragment: Fragment,
    private val listData: List<PlantData>,
    private val updateLayoutHeightCallback: AddingPlantFragment.UpdateLayoutHeightCallback
) : FragmentStateAdapter(fragment) {

    private val fragments = hashMapOf<Int, Fragment>()

    init {
        val data = PlantMockData.plant
        for (i in 0 until itemCount) {
            fragments[i] = PlantDescriptionFragment(data, updateLayoutHeightCallback)
        }
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]!!
    }

    fun getCurrentPlantNameAndData(position: Int): PlantData? {
        return (fragments[position] as GetPlantInfo).getPlantInfo()
    }

    override fun getItemCount() = 5
}