package com.entexy.gardenguru.ui.fragments.plant_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantCardViewModel @Inject constructor() : ViewModel() {

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf(
            GardenData(
                "Сад 1",
                "",
                "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
                arrayListOf(
                    PlantData(
                        "qwwqeew",
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
                )
            )
        )
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens


}
