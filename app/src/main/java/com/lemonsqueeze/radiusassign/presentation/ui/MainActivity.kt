package com.lemonsqueeze.radiusassign.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lemonsqueeze.radiusassign.R
import com.lemonsqueeze.radiusassign.presentation.viewmodel.FacilitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: FacilitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.callGetAllData()
        postFetchingData()
    }

    private fun postFetchingData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFacilities().collect { value ->
                when {
                    value.isLoading -> {
                        Log.d("Manish", "Loading....")
                    }

                    value.error.isNotBlank() -> {
                        Log.d("Manish", "Error.... ${value.error}")
                    }

                    value.facilities != null -> {
                        Log.d("Manish", "Data.... ${value.facilities}")
                    }
                }
            }
        }
    }
}