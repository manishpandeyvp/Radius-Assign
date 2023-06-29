package com.lemonsqueeze.radiusassign.data.model.exclusion

import android.os.Parcel
import android.os.Parcelable

data class ExclusionModel(
    val exclusion: List<ExclusionSubModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ExclusionSubModel)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(exclusion)
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