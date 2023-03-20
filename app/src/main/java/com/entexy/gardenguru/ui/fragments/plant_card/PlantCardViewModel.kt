package com.entexy.gardenguru.ui.fragments.plant_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.garden.models.GardenPlantData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardViewModel @Inject constructor() : ViewModel() {

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf(
            GardenData(
                "Сад 1",
                "",
                GardenData.SummerClimateSeason.JuneAugust,
                arrayListOf(
                    GardenPlantData(
                        "0",
                        "Иван",
                        "Кактус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    ),
                    GardenPlantData(
                        "0",
                        "Степан",
                        "Фикус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    ), GardenPlantData(
                        "0",
                        "Женя",
                        "Кактус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    )
                )
            ),
            GardenData(
                "Дом",
                "Дом",
                GardenData.SummerClimateSeason.JuneAugust,
                arrayListOf(
                    GardenPlantData(
                        "0",
                        "Василий",
                        "Кактус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    ),
                    GardenPlantData(
                        "0",
                        "Степан",
                        "Фикус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    ), GardenPlantData(
                        "0",
                        "Павел",
                        "Кактус",
                        "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                    )
                )
            )
        )
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens


}
