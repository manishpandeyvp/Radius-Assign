package com.lemonsqueeze.radiusassign.data.dto.exclusion

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.model.ExclusionModel

data class ExclusionItemDto(
    @SerializedName("facility_id")
    val facilityId: String,

    @SerializedName("options_id")
    val optionId: String
) {
    fun toExclusionModel(): ExclusionModel = ExclusionModel(
        facilityId = facilityId,
        optionId = optionId
    )
}