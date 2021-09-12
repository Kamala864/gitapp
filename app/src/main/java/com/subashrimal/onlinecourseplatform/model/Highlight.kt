package com.subashrimal.onlinecourseplatform.model

import android.os.Parcel
import android.os.Parcelable

data class Highlight(
    val courseTopic: String? = null,
    val coursePrice: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(courseTopic)
        parcel.writeString(coursePrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Highlight> {
        override fun createFromParcel(parcel: Parcel): Highlight {
            return Highlight(parcel)
        }

        override fun newArray(size: Int): Array<Highlight?> {
            return arrayOfNulls(size)
        }
    }
}
