package com.lemonsqueeze.radiusassign.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lemonsqueeze.radiusassign.data.dto.exclusion.ExclusionItemDto
import com.lemonsqueeze.radiusassign.data.dto.facility.FacilityItemDto

class DataConverter {

    @TypeConverter
    fun fromFacilitiesListToString(list: List<FacilityItemDto>?): String? {
        if (list == null) return null

        val gson = Gson()
        val type = object : TypeToken<List<FacilityItemDto?>?>() {}.type

        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStringToFacilitiesList(s: String?): List<FacilityItemDto>? {
        if (s == null) return null

        val gson = Gson()
        val type = object : TypeToken<List<FacilityItemDto?>?>() {}.type

        return gson.fromJson(s, type)
    }

    @TypeConverter
    fun fromExclusionListToString(list: List<List<ExclusionItemDto>>?): String? {
        if (list == null) return null

        val gson = Gson()
        val type = object : TypeToken<List<List<ExclusionItemDto?>?>?>() {}.type

        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStringToExclusionList(s: String?): List<List<ExclusionItemDto>>? {
        if (s == null) return null

        val gson = Gson()
        val type = object : TypeToken<List<List<ExclusionItemDto?>?>?>() {}.type

        return gson.fromJson(s, type)
    }
}