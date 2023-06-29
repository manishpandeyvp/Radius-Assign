package com.lemonsqueeze.radiusassign.domain.repository

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto

interface FacilitiesRepository {
    suspend fun getAllData(): RemoteDataDto
}