package com.lemonsqueeze.radiusassign.presentation.state

import com.lemonsqueeze.radiusassign.data.model.RemoteDataModel

data class FacilitiesState(
    val isLoading: Boolean = false,
    val facilities: RemoteDataModel? = null,
    val error: String = ""
)