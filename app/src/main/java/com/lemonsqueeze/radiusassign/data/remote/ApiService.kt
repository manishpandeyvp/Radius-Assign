package com.lemonsqueeze.radiusassign.data.remote

import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import retrofit2.http.GET

interface ApiService {

    @GET("iranjith4/ad-assignment/db")
    suspend fun getAllData(): RemoteDataDto

}