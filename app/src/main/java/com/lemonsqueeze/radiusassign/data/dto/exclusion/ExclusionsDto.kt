package com.lemonsqueeze.radiusassign.data.dto.exclusion

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.model.ExclusionModel

data class ExclusionsDto(
    @SerializedName("exclusions")
    val exclusions: List<ExclusionItemDto>
) {
    fun toExclusionModelList(): List<ExclusionModel> {
        return exclusions.map { it.toExclusionModel() }
    }
}