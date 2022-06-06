package com.example.gardenguru.data.garden.models

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.example.gardenguru.R
import com.example.gardenguru.core.Base

data class GardenData(
    val id: String,
    val name: String,
    val summerClimateSeason: SummerClimateSeason,
    val plants: ArrayList<GardenPlantData>,
    val participants: ArrayList<Participant> = arrayListOf(),
) : Base.DataObject, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        SummerClimateSeason.valueOf(parcel.readString()!!),
        arrayListOf<GardenPlantData>().apply {
            parcel.readList(this, GardenPlantData::class.java.classLoader)
        },
        arrayListOf<Participant>().apply {
            parcel.readList(this, Participant::class.java.classLoader)
        }
    )

    fun getGuruEmail():String{
        return participants.find { it.role == Participant.RoleInGarden.Guru}!!.email
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(summerClimateSeason.name)
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

        fun getSummerSeasonByValue(value: String): SummerClimateSeason {
            return when(value){
                SummerClimateSeason.JuneAugust.value -> SummerClimateSeason.JuneAugust
                SummerClimateSeason.DecemberFebruary.value -> SummerClimateSeason.DecemberFebruary
                else -> SummerClimateSeason.JuneAugust
            }
        }

        fun getSummerSeasonByStringValue(resources: Resources, value: String): SummerClimateSeason {
            return when(value){
                resources.getString(SummerClimateSeason.JuneAugust.stringNameRes) -> SummerClimateSeason.JuneAugust
                resources.getString(SummerClimateSeason.DecemberFebruary.stringNameRes) -> SummerClimateSeason.DecemberFebruary
                else -> SummerClimateSeason.JuneAugust
            }
        }
    }

    enum class SummerClimateSeason(val value: String, val stringNameRes: Int){
        JuneAugust("june_august", R.string.june_august_summer),
        DecemberFebruary("december_february", R.string.december_february_summer)
    }
}