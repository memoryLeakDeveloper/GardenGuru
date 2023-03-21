package com.entexy.gardenguru.data.garden.models

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.entexy.gardenguru.R

data class Participant(
    val id: String,
    val email: String,
    val role: RoleInGarden
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        RoleInGarden.valueOf(parcel.readString()!!)
    ) {
    }

    enum class RoleInGarden(val value: String, val nameStringRes: Int){
        Beginner("beginner", R.string.beginner),
        Experienced("experienced", R.string.experienced),
        Guru("guru", R.string.guru)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(role.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Participant> {
        override fun createFromParcel(parcel: Parcel): Participant {
            return Participant(parcel)
        }

        override fun newArray(size: Int): Array<Participant?> {
            return arrayOfNulls(size)
        }


        fun getRoleByValue(role: String): RoleInGarden {
            return when(role){
                RoleInGarden.Guru.value -> RoleInGarden.Guru
                RoleInGarden.Experienced.value -> RoleInGarden.Experienced
                else -> RoleInGarden.Guru
            }
        }

        fun getRoleByStringValue(role: String, resources: Resources): RoleInGarden {
            return when(role){
                resources.getString(RoleInGarden.Guru.nameStringRes) -> RoleInGarden.Guru
                resources.getString(RoleInGarden.Experienced.nameStringRes) -> RoleInGarden.Experienced
                else -> RoleInGarden.Beginner
            }
        }
    }


}