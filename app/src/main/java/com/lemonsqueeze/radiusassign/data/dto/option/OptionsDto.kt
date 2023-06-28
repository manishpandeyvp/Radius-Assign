package com.lemonsqueeze.radiusassign.data.dto.option

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.model.OptionModel

data class OptionsDto(
    @SerializedName("options")
    val options: List<OptionItemDto>
) {
    fun toOptionModelList(): List<OptionModel> {
        return options.map { it.toOptionModel() }
    }
}