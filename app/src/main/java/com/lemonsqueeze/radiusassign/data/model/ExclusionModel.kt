package com.lemonsqueeze.radiusassign.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ExclusionModel(
    val facilityId: String?,
    val optionId: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(facilityId)
        parcel.writeString(optionId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExclusionModel> {
        override fun createFromParcel(parcel: Parcel): ExclusionModel {
            return ExclusionModel(parcel)
        }

        override fun newArray(size: Int): Array<ExclusionModel?> {
            return arrayOfNulls(size)
        }
    }

}