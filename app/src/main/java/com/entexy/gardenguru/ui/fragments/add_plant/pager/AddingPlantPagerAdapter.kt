import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo
import com.entexy.gardenguru.ui.fragments.add_plant.client.ClientPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.description.PlantDescriptionFragment
import java.util.*

class AddingPlantPagerAdapter(fragment: Fragment, private val listData: List<PlantData>, private val updateLayoutHeightCallback: AddingPlantFragment.UpdateLayoutHeightCallback) : FragmentStateAdapter(fragment) {

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