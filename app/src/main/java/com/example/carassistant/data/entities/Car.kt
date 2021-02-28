package com.example.carassistant.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car (
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val nazivModela: String = "",
    val motor: String = "",
    val litri: String = "",
    val snagaMotora: String = "",
    val godina: String = "",
    val slabosti: String = "",
    val brand: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nazivModela)
        parcel.writeString(motor)
        parcel.writeString(litri)
        parcel.writeString(snagaMotora)
        parcel.writeString(godina)
        parcel.writeString(slabosti)
        parcel.writeString(brand)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}
