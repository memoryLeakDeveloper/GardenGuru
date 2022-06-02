package com.example.gardenguru.data.garden.models

import android.os.Parcel
import android.os.Parcelable
import com.example.gardenguru.core.Base

data class Participant(
    val id: String,
    val email: String,
    val role: RoleInGarden
): Base.CloudObject, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        RoleInGarden.valueOf(parcel.readString()!!)
    ) {
    }

    enum class RoleInGarden(val value: String){
        Newbie("newbie"),
        Experienced("experienced"),
        Guru("guru")
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


        fun getRoleByValue(role: String): RoleInGarden{
            return when(role){
                RoleInGarden.Guru.value -> RoleInGarden.Guru
                RoleInGarden.Experienced.value -> RoleInGarden.Experienced
                else -> RoleInGarden.Guru
            }
        }
    }


}