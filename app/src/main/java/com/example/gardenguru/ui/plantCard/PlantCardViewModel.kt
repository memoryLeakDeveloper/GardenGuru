package com.example.gardenguru.ui.plantCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardenguru.data.garden.GardenData
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardViewModel @Inject constructor() : ViewModel() {

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf(
            GardenData(
                "Сад 1",
                "",
                arrayListOf(
                    PlantData(
                        "0",
                        0,
                        "Иван",
                        "Кактус",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    ),
                    PlantData(
                        "0",
                        0,
                        "Федя",
                        "Бегония",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    ),
                    PlantData(
                        "0",
                        0,
                        "Ихорь",
                        "Фикус",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                )
            ),
            GardenData(
                "Дом",
                "ecat@gmail.com",
                arrayListOf(
                    PlantData(
                        "0",
                        0,
                        "Вася",
                        "Кактус",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    ),
                    PlantData(
                        "0",
                        0,
                        "Леонид",
                        "Бегония",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    ),
                    PlantData(
                        "0",
                        0,
                        "Иван",
                        "Фикус",
                        arrayListOf(
                            PhotoData(
                                "0",
                                "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                            )
                        ),
                        SunRelationData(0, ""),
                        arrayListOf(),
                        arrayListOf(),
                        arrayListOf(),
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                )
            )
        )
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens


}
