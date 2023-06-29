package com.lemonsqueeze.radiusassign.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lemonsqueeze.radiusassign.R
import com.lemonsqueeze.radiusassign.presentation.viewmodel.FacilitiesViewModel
import com.lemonsqueeze.radiusassign.utils.Constants
import com.lemonsqueeze.radiusassign.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: FacilitiesViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = SharedPref.getInstance(this)

        checkForInterval()
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
                        sharedPref.edit().putLong(Constants.LAST_TIME_SAVED, System.currentTimeMillis()).apply()
                    }
                }
            }
        }
    }

    private fun checkForInterval() {
        val timeInMillis: Long = System.currentTimeMillis()
        sharedPref.getLong(Constants.LAST_TIME_SAVED, 0L).let {
            if (it == 0L) {
                viewModel.getAllDataFromRemote()
                postFetchingData()
            } else if (timeInMillis.minus(it) >= Constants.TIME_24_HR) {
                viewModel.getAllDataFromRemote()
                postFetchingData()
            } else {
                viewModel.getAllDataFromLocal()
                postFetchingData()
            }
        }
    }
}