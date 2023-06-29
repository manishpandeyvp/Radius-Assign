package com.lemonsqueeze.radiusassign.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromLocalUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataFromRemoteUseCase
import com.lemonsqueeze.radiusassign.domain.usecase.InsertDataToLocalUseCase
import com.lemonsqueeze.radiusassign.presentation.state.FacilitiesState
import com.lemonsqueeze.radiusassign.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacilitiesViewModel @Inject constructor(
    private val getAllDataFromRemoteUseCase: GetAllDataFromRemoteUseCase,
    private val insertDataToLocalUseCase: InsertDataToLocalUseCase,
    private val getAllDataFromLocalUseCase: GetAllDataFromLocalUseCase
) : ViewModel() {
    private val facilities = MutableStateFlow(FacilitiesState())
    fun getFacilities(): StateFlow<FacilitiesState> = facilities

    fun getAllDataFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        getAllDataFromRemoteUseCase.execute().collect { it ->
            when (it) {
                is Response.Loading -> {
                    Log.d("manish_re_lo", "Loading...")
                    facilities.value = FacilitiesState(isLoading = true)
                }

                is Response.Success -> {
                    Log.d("manish_re_data", "${it.data}")
                    facilities.value = FacilitiesState(facilities = it.data?.toRemoteDataModel())
                    it.data?.let { insertDataToLocalUseCase.execute(it) }
                }

                is Response.Error -> {
                    Log.d("manish_re_er", "${it.message}")
                    facilities.value =
                        FacilitiesState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    fun getAllDataFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        getAllDataFromLocalUseCase.execute().collect { it ->
            when (it) {
                is Response.Loading -> {
                    Log.d("manish_db_load", "Loading...")
                    facilities.value = FacilitiesState(isLoading = true)
                }

                is Response.Success -> {
                    Log.d("manish_db_data", "${it.data}")
                    facilities.value = FacilitiesState(facilities = it.data?.toRemoteDataModel())
                }

                is Response.Error -> {
                    Log.d("manish_db_er", "${it.message}")
                    facilities.value = FacilitiesState(error = it.message ?: "An unexpected error occurred")
                    getAllDataFromRemote()
                }
            }
        }
    }
}