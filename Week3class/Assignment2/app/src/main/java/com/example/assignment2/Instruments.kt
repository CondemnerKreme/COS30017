package com.example.assignment2

import android.os.Parcel
import android.os.Parcelable

data class Instrument(
    val name: String,
    val type: String,
    val rating: Float,
    val price: Int,
    var available: Boolean = true,
    val imageResId: Int = R.drawable.ic_launcher_foreground // Default fallback
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeFloat(rating)
        parcel.writeInt(price)
        parcel.writeByte(if (available) 1 else 0)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Instrument> {
        override fun createFromParcel(parcel: Parcel): Instrument {
            return Instrument(parcel)
        }

        override fun newArray(size: Int): Array<Instrument?> {
            return arrayOfNulls(size)
        }
    }
}