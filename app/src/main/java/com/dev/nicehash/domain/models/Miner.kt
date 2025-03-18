package com.dev.nicehash.domain.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Alex Gladkov on 23.06.18.
 * Data model for Miner entity in domain layer
 */
data class Miner(val id: Int, val name: String, var hash: String, var isSelected: Boolean): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(hash)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Miner> {
        fun getDefaultInstance(): Miner {
            return Miner(id = -10, name = "Demo miner", hash = "Demo miner", isSelected = true)
        }

        override fun createFromParcel(parcel: Parcel): Miner {
            return Miner(parcel)
        }

        override fun newArray(size: Int): Array<Miner?> {
            return arrayOfNulls(size)
        }
    }
}