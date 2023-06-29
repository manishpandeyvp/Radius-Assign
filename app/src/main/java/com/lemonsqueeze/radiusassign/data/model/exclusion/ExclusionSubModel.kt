package com.lemonsqueeze.radiusassign.data.model.exclusion

import android.os.Parcel
import android.os.Parcelable

data class ExclusionSubModel(
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

    companion object CREATOR : Parcelable.Creator<ExclusionSubModel> {
        override fun createFromParcel(parcel: Parcel): ExclusionSubModel {
            return ExclusionSubModel(parcel)
        }

        override fun newArray(size: Int): Array<ExclusionSubModel?> {
            return arrayOfNulls(size)
        }
    }

}