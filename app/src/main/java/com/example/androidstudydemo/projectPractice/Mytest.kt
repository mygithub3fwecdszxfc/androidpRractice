package com.example.androidstudydemo.projectPractice

import android.os.Parcel
import android.os.Parcelable

data class Mytest(val name: String, val age: Int) : Parcelable {
    private constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Mytest> {
        override fun createFromParcel(parcel: Parcel): Mytest = Mytest(parcel)

        override fun newArray(size: Int): Array<Mytest?> = arrayOfNulls(size)
    }
}
