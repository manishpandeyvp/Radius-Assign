package com.lemonsqueeze.radiusassign.data.repository

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.data.local.AssignDao
import com.lemonsqueeze.radiusassign.data.remote.ApiService
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import javax.inject.Inject

class FacilitiesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: AssignDao
): FacilitiesRepository {
    override suspend fun getAllDataFromRemote(): RemoteDataDto {
        return apiService.getAllData()
    }

    override suspend fun getAllDataFromLocal(): RemoteDataDto? {
        return dao.getAllData()
    }

    override suspend fun insertDataToDb(data: RemoteDataDto) {
        val d = dao.getAllData()
        if (d == null) dao.insertData(data)
        else {
            dao.deleteData(d)
            dao.insertData(data)
        }
    }
}