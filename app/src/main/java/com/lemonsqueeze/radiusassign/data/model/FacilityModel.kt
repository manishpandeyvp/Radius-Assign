package com.lemonsqueeze.radiusassign.data.model

import android.os.Parcel
import android.os.Parcelable

data class FacilityModel(
    val facilityId: String?,
    val name: String?,
    val options: List<OptionModel>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(OptionModel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(facilityId)
        parcel.writeString(name)
        parcel.writeTypedList(options)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FacilityModel> {
        override fun createFromParcel(parcel: Parcel): FacilityModel {
            return FacilityModel(parcel)
        }

        override fun newArray(size: Int): Array<FacilityModel?> {
            return arrayOfNulls(size)
        }
    }

}