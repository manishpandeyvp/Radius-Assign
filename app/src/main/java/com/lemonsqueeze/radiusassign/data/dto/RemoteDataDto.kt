package com.lemonsqueeze.radiusassign.data.dto

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.dto.exclusion.ExclusionsDto
import com.lemonsqueeze.radiusassign.data.dto.facility.FacilitiesDto
import com.lemonsqueeze.radiusassign.data.model.RemoteDataModel

data class RemoteDataDto(
    @SerializedName("facilities")
    val facilities: FacilitiesDto,

    @SerializedName("exclusions")
    val exclusions: ExclusionsDto
) {
    fun toRemoteDataModel(): RemoteDataModel = RemoteDataModel(
        facilities = facilities.toFacilitiesModelList(),
        exclusions = exclusions.toExclusionModelList()
    )
}