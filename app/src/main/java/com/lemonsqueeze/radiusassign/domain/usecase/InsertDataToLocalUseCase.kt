package com.lemonsqueeze.radiusassign.domain.usecase

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import javax.inject.Inject

interface InsertDataToLocalUseCase {
    suspend fun execute(data: RemoteDataDto)
}

class InsertDataToLocalUseCaseImpl @Inject constructor(
    private val repository: FacilitiesRepository
) : InsertDataToLocalUseCase {
    override suspend fun execute(data: RemoteDataDto) {
        repository.insertDataToDb(data)
    }
}