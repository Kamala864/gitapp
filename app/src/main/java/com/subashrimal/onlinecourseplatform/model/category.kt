package com.subashrimal.onlinecourseplatform.model

import android.os.Parcel
import android.os.Parcelable

data class category(
    val className: String? = null,
    val numOfCourse: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(className)
        parcel.writeString(numOfCourse)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<category> {
        override fun createFromParcel(parcel: Parcel): category {
            return category(parcel)
        }

        override fun newArray(size: Int): Array<category?> {
            return arrayOfNulls(size)
        }
    }
}
