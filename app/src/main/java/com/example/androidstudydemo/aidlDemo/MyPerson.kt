package com.example.androidstudydemo.aidlDemo

import android.os.Parcel
import android.os.Parcelable

data class MyPerson(
    val name: String,
    val age: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyPerson> {
        override fun createFromParcel(parcel: Parcel): MyPerson {
            return MyPerson(parcel)
        }

        override fun newArray(size: Int): Array<MyPerson?> {
            return arrayOfNulls(size)
        }
    }
}