package com.lemonsqueeze.radiusassign.domain.usecase

import com.lemonsqueeze.radiusassign.data.model.RemoteDataModel
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import com.lemonsqueeze.radiusassign.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetAllDataUseCase {
    fun execute(): Flow<Response<RemoteDataModel>>
}

class GetAllDataUseCaseImpl @Inject constructor(
    private val facilitiesRepository: FacilitiesRepository
) : GetAllDataUseCase {
    override fun execute(): Flow<Response<RemoteDataModel>> = flow {
        try {
            emit(Response.Loading())
            val data = facilitiesRepository.getAllData().toRemoteDataModel()
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