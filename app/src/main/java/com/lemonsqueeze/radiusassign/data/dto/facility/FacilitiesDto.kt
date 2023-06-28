package com.lemonsqueeze.radiusassign.data.dto.facility

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.model.FacilityModel

data class FacilitiesDto(
    @SerializedName("facilities")
    val facilities: List<FacilityItemDto>
) {
    fun toFacilitiesModelList(): List<FacilityModel> {
        return facilities.map { it.toFacilityModel() }
    }
}