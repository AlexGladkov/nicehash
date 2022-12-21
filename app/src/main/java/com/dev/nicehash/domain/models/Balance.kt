package com.dev.nicehash.domain.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Alex Gladkov on 15.07.18.
 * Model for balance cell (domain)
 */
data class Balance(val id: Int, val title: String, var value: Float, val btc: Double,
                   var incomeValue: Float, val incomeBtc: Double, val suffix: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readDouble(),
            parcel.readFloat(),
            parcel.readDouble(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeFloat(value)
        parcel.writeDouble(btc)
        parcel.writeFloat(incomeValue)
        parcel.writeDouble(incomeBtc)
        parcel.writeString(suffix)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Balance> {
        override fun createFromParcel(parcel: Parcel): Balance {
            return Balance(parcel)
        }

        override fun newArray(size: Int): Array<Balance?> {
            return arrayOfNulls(size)
        }
    }

}