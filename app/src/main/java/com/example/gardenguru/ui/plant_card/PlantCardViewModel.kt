package com.example.gardenguru.ui.plant_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.data.garden.models.GardenPlantData
import javax.inject.Inject

class PlantCardViewModel @Inject constructor() : ViewModel() {

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf(
            GardenData(
                "Сад 1",
                "",
                "",
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
                "ecat@gmail.com",
                "Дом",
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
