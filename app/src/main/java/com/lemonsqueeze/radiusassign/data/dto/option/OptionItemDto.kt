package com.lemonsqueeze.radiusassign.data.dto.option

import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.model.OptionModel

data class OptionItemDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("id")
    val id: String
) {
    fun toOptionModel(): OptionModel = OptionModel(
        name = name,
        icon = icon,
        id = id
    )
}