package com.lemonsqueeze.radiusassign.presentation.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup.LayoutParams
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.lemonsqueeze.radiusassign.R
import com.lemonsqueeze.radiusassign.data.model.OptionModel
import com.lemonsqueeze.radiusassign.databinding.ActivityMainBinding
import com.lemonsqueeze.radiusassign.presentation.viewmodel.FacilitiesViewModel
import com.lemonsqueeze.radiusassign.utils.Constants
import com.lemonsqueeze.radiusassign.utils.RadioButtonDrawable
import com.lemonsqueeze.radiusassign.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: FacilitiesViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref.getInstance(this)

        checkForInterval()
    }

    override fun onResume() {
        super.onResume()

        binding.ivNext.setOnClickListener {
            val size = viewModel.remoteModel?.facilities?.size ?: 0
            val key = viewModel.remoteModel?.facilities?.get(viewModel.count)?.facilityId
            key?.let {
                if (!viewModel.getSelectedValues().containsKey(it)) {
                    binding.tvNotSelected.visibility = View.VISIBLE
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener


            if (viewModel.count == size - 1) {
                viewModel.count = 0
                binding.rg.removeAllViews()
                setRadioGroupForEachFacility()
                viewModel.resetSelectedValues()
                binding.tvNotSelected.visibility = View.GONE
                return@setOnClickListener
            }

            binding.tvNotSelected.visibility = View.GONE
            viewModel.count++
            binding.rg.removeAllViews()
            setRadioGroupForEachFacility()
        }
    }


    private fun postFetchingData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFacilities().collect { value ->
                when {
                    value.isLoading -> {
                        toggleVisibilityOfViews(show = false, error = false)
                    }

                    value.error.isNotBlank() -> {
                        toggleVisibilityOfViews(show = false, error = true)
                    }

                    value.facilities != null -> {
                        value.facilities.let { viewModel.remoteModel = it }
                        toggleVisibilityOfViews(show = true, error = false)
                        setRadioGroupForEachFacility()
                        sharedPref.edit()
                            .putLong(Constants.LAST_TIME_SAVED, System.currentTimeMillis()).apply()
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

    private fun setRadioGroupForEachFacility() {
        viewModel.remoteModel?.let { data ->
            data.facilities?.get(viewModel.count)?.options?.let { options ->
                val facilityId = data.facilities[viewModel.count].facilityId
                for (option in options) {
                    val flag = checkIfOptionToBeShown(option)
                    if (flag) setRadioButton(option, facilityId)
                }
            }
        }
    }

    private fun checkIfOptionToBeShown(option: OptionModel): Boolean {
        viewModel.remoteModel?.exclusions?.let { exclusions ->
            for (exclusion in exclusions) {
                if (exclusion.exclusion?.get(1)?.facilityId.equals((viewModel.count + 1).toString())) {
                    if (exclusion.exclusion?.get(1)?.optionId.equals(option.id)) {
                        val lastOptionId =
                            viewModel.getSelectedValues()[exclusion.exclusion?.get(0)?.facilityId]
                                ?: ""
                        if (exclusion.exclusion?.get(0)?.optionId.equals(lastOptionId)) return false
                    }
                }
            }
        }

        return true
    }

    private fun setRadioButton(option: OptionModel, facilityId: String?) {
        val rb = RadioButton(applicationContext)

        rb.id = View.generateViewId()
        rb.text = option.name

        val params = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            0.5f
        )

        rb.layoutParams = params
        rb.background =
            ResourcesCompat.getDrawable(resources, R.drawable.radio_button_custom_bg, null)
        rb.buttonDrawable = ResourcesCompat.getDrawable(
            resources,
            RadioButtonDrawable().getDrawable(option.icon!!),
            null
        )
        rb.setPadding(0, 0, 30, 0)
        rb.setOnClickListener {
            facilityId?.let { viewModel.setSelectedValue(facilityId, option.id!!) }
        }

        binding.rg.addView(rb)
    }

    private fun toggleVisibilityOfViews(show: Boolean, error: Boolean) {
        if (error) {
            binding.progressBar.visibility = View.GONE
            binding.cvMain.visibility = View.GONE
            binding.tvError.visibility = View.VISIBLE
            return
        }

        binding.progressBar.visibility = if (show) View.GONE else View.VISIBLE
        binding.cvMain.visibility = if (show) View.VISIBLE else View.GONE
    }
}