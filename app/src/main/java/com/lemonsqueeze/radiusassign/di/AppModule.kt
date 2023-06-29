package com.lemonsqueeze.radiusassign.di

import com.lemonsqueeze.radiusassign.data.remote.ApiService
import com.lemonsqueeze.radiusassign.data.repository.FacilitiesRepositoryImpl
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataUseCaseImpl
import com.lemonsqueeze.radiusassign.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFacilityRepository(apiService: ApiService): FacilitiesRepository {
        return FacilitiesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetAllDataUseCase(facilitiesRepository: FacilitiesRepository): GetAllDataUseCase {
        return GetAllDataUseCaseImpl(facilitiesRepository)
    }
}