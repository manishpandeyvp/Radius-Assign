package com.lemonsqueeze.radiusassign.data.dto.facility

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.dto.option.OptionItemDto
import com.lemonsqueeze.radiusassign.data.model.FacilityModel

data class FacilityItemDto(
    @SerializedName("facility_id")
    val facilityId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("options")
    val options: List<OptionItemDto>
) {
    fun toFacilityModel(): FacilityModel = FacilityModel(
        facilityId = facilityId,
        name = name,
        options = options.map { it.toOptionModel() }
    )
}