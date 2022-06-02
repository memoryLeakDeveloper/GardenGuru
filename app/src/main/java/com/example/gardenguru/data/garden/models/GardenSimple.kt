package com.example.gardenguru.data.garden.models

import android.os.Parcel
import android.os.Parcelable
import com.example.gardenguru.core.Base

data class GardenSimple(
    val name: String,
    val gardenOwner: String,
) : Base.DataObject, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(gardenOwner)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GardenSimple> {
        override fun createFromParcel(parcel: Parcel): GardenSimple {
            return GardenSimple(parcel)
        }

        override fun newArray(size: Int): Array<GardenSimple?> {
            return arrayOfNulls(size)
        }
    }
}
