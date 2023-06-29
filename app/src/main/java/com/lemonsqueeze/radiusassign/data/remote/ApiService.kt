package com.lemonsqueeze.radiusassign.data.remote

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getAllData(): RemoteDataDto

}