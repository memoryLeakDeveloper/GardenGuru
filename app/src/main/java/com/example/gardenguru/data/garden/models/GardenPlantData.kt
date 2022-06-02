package com.example.gardenguru.data.garden.models

import android.os.Parcel
import android.os.Parcelable
import com.example.gardenguru.core.Base
import com.example.gardenguru.data.plant.PlantData

data class GardenPlantData(
    val id: String,
    val name: String,
    val plant: String,
    val photo: String
) : Base.DataObject, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(plant)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GardenPlantData> {
        override fun createFromParcel(parcel: Parcel): GardenPlantData {
            return GardenPlantData(parcel)
        }

        override fun newArray(size: Int): Array<GardenPlantData?> {
            return arrayOfNulls(size)
        }
    }
}