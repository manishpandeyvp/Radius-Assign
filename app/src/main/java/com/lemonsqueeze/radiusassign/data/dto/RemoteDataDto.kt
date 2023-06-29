package com.lemonsqueeze.radiusassign.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lemonsqueeze.radiusassign.data.dto.exclusion.ExclusionItemDto
import com.lemonsqueeze.radiusassign.data.dto.facility.FacilityItemDto
import com.lemonsqueeze.radiusassign.data.model.RemoteDataModel
import com.lemonsqueeze.radiusassign.data.model.exclusion.ExclusionModel

@Entity(tableName = RemoteDataDto.TABLE_NAME)
data class RemoteDataDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("facilities")
    val facilities: List<FacilityItemDto>,

    @SerializedName("exclusions")
    val exclusions: List<List<ExclusionItemDto>>
) {
    fun toRemoteDataModel(): RemoteDataModel = RemoteDataModel(
        facilities = facilities.map { it.toFacilityModel() },
        exclusions = exclusions.map {
            ExclusionModel( it.map { exclusionItem ->
                exclusionItem.toExclusionModel()
            } )
        }
    )

    companion object {
        const val TABLE_NAME = "RemoteData"
    }
}