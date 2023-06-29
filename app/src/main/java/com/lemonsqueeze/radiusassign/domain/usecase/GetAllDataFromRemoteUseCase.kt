package com.lemonsqueeze.radiusassign.domain.usecase

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import com.lemonsqueeze.radiusassign.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetAllDataFromRemoteUseCase {
    fun execute(): Flow<Response<RemoteDataDto>>
}

class GetAllDataFromRemoteUseCaseImpl @Inject constructor(
    private val facilitiesRepository: FacilitiesRepository
) : GetAllDataFromRemoteUseCase {
    override fun execute(): Flow<Response<RemoteDataDto>> = flow {
        try {
            emit(Response.Loading())
            val data = facilitiesRepository.getAllDataFromRemote()
            emit(Response.Success(data = data))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Response.Error(message = "Error Occurred while fetching the data!"))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Response.Error(message = "Error Occurred while fetching the data!"))
        }
    }

}