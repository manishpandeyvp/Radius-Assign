package com.lemonsqueeze.radiusassign.di

import android.content.Context
import com.lemonsqueeze.radiusassign.data.local.AssignDao
import com.lemonsqueeze.radiusassign.data.local.AssignDatabase
import com.lemonsqueeze.radiusassign.data.remote.ApiService
import com.lemonsqueeze.radiusassign.data.repository.FacilitiesRepositoryImpl
import com.lemonsqueeze.radiusassign.domain.repository.FacilitiesRepository
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromLocalUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromLocalUseCaseImpl
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromRemoteUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromRemoteUseCaseImpl
import com.lemonsqueeze.radiusassign.domain.usecase.InsertDataToLocalUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.InsertDataToLocalUseCaseImpl
import com.lemonsqueeze.radiusassign.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideFacilityRepository(apiService: ApiService, dao: AssignDao): FacilitiesRepository {
        return FacilitiesRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideGetAllDataUseCase(facilitiesRepository: FacilitiesRepository): GetAllDataFromRemoteUseCase {
        return GetAllDataFromRemoteUseCaseImpl(facilitiesRepository)
    }

    @Provides
    @Singleton
    fun provideAssignDao(@ApplicationContext context: Context): AssignDao {
        return AssignDatabase.getInstance(context).dao()
    }

    @Provides
    @Singleton
    fun provideGetAllDataFromLocalUseCase(facilitiesRepository: FacilitiesRepository): GetAllDataFromLocalUseCase {
        return GetAllDataFromLocalUseCaseImpl(facilitiesRepository)
    }

    @Provides
    @Singleton
    fun provideInsertDataToLocalUseCase(facilitiesRepository: FacilitiesRepository): InsertDataToLocalUseCase {
        return InsertDataToLocalUseCaseImpl(facilitiesRepository)
    }
}