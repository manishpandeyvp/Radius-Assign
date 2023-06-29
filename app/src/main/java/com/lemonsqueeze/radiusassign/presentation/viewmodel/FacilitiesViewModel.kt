package com.lemonsqueeze.radiusassign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lemonsqueeze.radiusassign.domain.usecase.GetAllDataUseCase
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
    private val getAllDataUseCase: GetAllDataUseCase
) : ViewModel() {
    private val facilities = MutableStateFlow(FacilitiesState())
    fun getFacilities(): StateFlow<FacilitiesState> = facilities

    fun callGetAllData() = viewModelScope.launch(Dispatchers.IO) {
        getAllDataUseCase.execute().collect {
            when (it) {
                is Response.Loading -> {
                    facilities.value = FacilitiesState(isLoading = true)
                }

                is Response.Success -> {
                    facilities.value = FacilitiesState(facilities = it.data)
                }

                is Response.Error -> {
                    facilities.value =
                        FacilitiesState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}