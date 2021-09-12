package com.subashrimal.onlinecourseplatform.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    val _id: String? = null,
    val courseTitle: String? = null,
    val courseContent: String? = null,
    val courseType: String? = null,
    val coursePrice: String? = null,
    val courseAuthor: String? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var CourseId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        CourseId = parcel.readInt()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(courseTitle)
        parcel.writeString(courseContent)
        parcel.writeString(courseType)
        parcel.writeString(coursePrice)
        parcel.writeString(courseAuthor)
        parcel.writeInt(CourseId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}