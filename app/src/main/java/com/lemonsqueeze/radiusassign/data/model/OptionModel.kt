package com.lemonsqueeze.radiusassign.data.model

import android.os.Parcel
import android.os.Parcelable

data class OptionModel(
    val name: String?,
    val icon: String?,
    val id: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(icon)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OptionModel> {
        override fun createFromParcel(parcel: Parcel): OptionModel {
            return OptionModel(parcel)
        }

        override fun newArray(size: Int): Array<OptionModel?> {
            return arrayOfNulls(size)
        }
    }

}
