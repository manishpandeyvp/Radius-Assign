package com.lemonsqueeze.radiusassign.domain.repository

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto

interface FacilitiesRepository {
    suspend fun getAllDataFromRemote(): RemoteDataDto
    suspend fun getAllDataFromLocal(): RemoteDataDto?
    suspend fun insertDataToDb(data: RemoteDataDto)
}