package com.lemonsqueeze.radiusassign.data.model

import android.os.Parcel
import android.os.Parcelable
import com.lemonsqueeze.radiusassign.data.model.exclusion.ExclusionModel

data class RemoteDataModel(
    val facilities: List<FacilityModel>?,
    val exclusions: List<ExclusionModel>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(FacilityModel),
        parcel.createTypedArrayList(ExclusionModel)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(facilities)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RemoteDataModel> {
        override fun createFromParcel(parcel: Parcel): RemoteDataModel {
            return RemoteDataModel(parcel)
        }

        override fun newArray(size: Int): Array<RemoteDataModel?> {
            return arrayOfNulls(size)
        }
    }

}