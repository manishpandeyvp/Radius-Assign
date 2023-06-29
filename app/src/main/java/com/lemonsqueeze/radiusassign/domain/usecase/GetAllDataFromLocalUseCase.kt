package com.lemonsqueeze.radiusassign.domain.usecase

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import com.lemonsqueeze.radiusassign.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetAllDataFromLocalUseCase {
    fun execute(): Flow<Response<RemoteDataDto>>
}

class GetAllDataFromLocalUseCaseImpl @Inject constructor(
    private val repository: FacilitiesRepository
) : GetAllDataFromLocalUseCase {
    override fun execute(): Flow<Response<RemoteDataDto>> = flow {
        try {
            emit(Response.Loading())
            val data = repository.getAllDataFromLocal()
            data?.let { emit(Response.Success(it)) }
                ?: emit(Response.Error(message = "No Data Available!"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(message = e.message.toString()))
        }
    }
}