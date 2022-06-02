package com.example.gardenguru.data.garden.models

import android.os.Parcel
import android.os.Parcelable
import com.example.gardenguru.core.Base

data class GardenData(
    val id: String,
    val guru: String,
    val name: String,
    val plants: ArrayList<GardenPlantData>,
    val participants: ArrayList<Participant> = arrayListOf(),
) : Base.DataObject, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        arrayListOf<GardenPlantData>().apply {
            parcel.readList(this, GardenPlantData::class.java.classLoader)
        },
        arrayListOf<Participant>().apply {
            parcel.readList(this, Participant::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(guru)
        parcel.writeString(name)
        parcel.writeList(plants.toList())
        parcel.writeList(participants.toList())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GardenData> {
        override fun createFromParcel(parcel: Parcel): GardenData {
            return GardenData(parcel)
        }

        override fun newArray(size: Int): Array<GardenData?> {
            return arrayOfNulls(size)
        }
    }
}