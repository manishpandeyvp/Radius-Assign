package com.lemonsqueeze.radiusassign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lemonsqueeze.radiusassign.data.model.RemoteDataModel
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

    private var selectedValues: HashMap<String, String> = HashMap()
    fun getSelectedValues(): HashMap<String, String> = selectedValues
    fun resetSelectedValues() {
        selectedValues = HashMap()
    }

    fun setSelectedValue(key: String, value: String) {
        selectedValues[key] = value
    }

    var count = 0
    var remoteModel: RemoteDataModel? = null

    fun getAllDataFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        getAllDataFromRemoteUseCase.execute().collect { it ->
            when (it) {
                is Response.Loading -> {
                    facilities.value = FacilitiesState(isLoading = true)
                }

                is Response.Success -> {
                    facilities.value = FacilitiesState(facilities = it.data?.toRemoteDataModel())
                    it.data?.let {
                        insertDataToLocalUseCase.execute(it)
                    }
                }

                is Response.Error -> {
                    facilities.value =
                        FacilitiesState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    fun getAllDataFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        getAllDataFromLocalUseCase.execute().collect {
            when (it) {
                is Response.Loading -> {
                    facilities.value = FacilitiesState(isLoading = true)
                }

                is Response.Success -> {
                    facilities.value = FacilitiesState(facilities = it.data?.toRemoteDataModel())
                }

                is Response.Error -> {
                    facilities.value =
                        FacilitiesState(error = it.message ?: "An unexpected error occurred")
                    getAllDataFromRemote()
                }
            }
        }
    }
}