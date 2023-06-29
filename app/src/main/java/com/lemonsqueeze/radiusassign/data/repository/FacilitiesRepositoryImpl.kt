package com.lemonsqueeze.radiusassign.data.repository

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.data.remote.ApiService
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import javax.inject.Inject

class FacilitiesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): FacilitiesRepository {
    override suspend fun getAllData(): RemoteDataDto {
        return apiService.getAllData()
    }
}