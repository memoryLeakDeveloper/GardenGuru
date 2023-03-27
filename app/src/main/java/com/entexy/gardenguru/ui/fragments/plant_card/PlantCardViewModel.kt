package com.entexy.gardenguru.ui.fragments.plant_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.ui.PlantMockData
import dagger.hilt.android.lifecycle.HiltViewModel
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
                    PlantMockData.plantsData.first()
                )
            )
        )
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens


}
