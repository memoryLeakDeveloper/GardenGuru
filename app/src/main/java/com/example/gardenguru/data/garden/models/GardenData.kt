package com.example.gardenguru.data.garden.models

import android.content.res.Resources
import android.os.Parcelable
import com.example.gardenguru.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class GardenData(
    val id: String,
    val name: String,
    val summerClimateSeason: SummerClimateSeason,
    val plants: ArrayList<GardenPlantData>,
    val participants: ArrayList<Participant> = arrayListOf(),
): Parcelable {
    fun getGuruEmail():String{
        return participants.find { it.role == Participant.RoleInGarden.Guru}!!.email
    }

    companion object {
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