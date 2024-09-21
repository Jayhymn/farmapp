package com.jayhymn.farmapp.ui.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.jayhymn.farmapp.CropList
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.databinding.ActivityFarmRegistrationBinding
import com.jayhymn.farmapp.domain.InputField
import com.jayhymn.farmapp.domain.ValidationResult
import com.jayhymn.farmapp.ui.viewmodels.FarmerRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmRegistrationActivity : AppCompatActivity() {
    private val viewModel by viewModels<FarmerRegistrationViewModel>()
    lateinit var binding: ActivityFarmRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmRegistrationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        enableEdgeToEdge()

        setUpCropDropDown()

        binding.toolbarIcon.setOnClickListener { finish() }

        binding.btnSubmit.setOnClickListener {
            // temporary disabled button so form isn't submitted more than once
            binding.btnSubmit.isEnabled = false

            viewModel.onRegisterButtonClicked(
                binding.editTextFarmerFirstName.text.toString(),
                binding.editTextFarmerLastName.text.toString(),
                binding.editTextFarmerPhone.text.toString(),
                binding.editTextCropType.text.toString(),
            )

            lifecycleScope.launch {
                delay(2000) //enable after 2 seconds
                binding.btnSubmit.isEnabled = true
            }
        }
        observeViewModelState()
    }

    fun observeViewModelState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    // Clear previous errors
                    binding.inputLayoutFarmerFirstName.error = null
                    binding.inputLayoutFarmerLastName.error = null
                    binding.inputLayoutFarmerPhone.error = null
                    binding.inputLayoutCropType.error = null

                    // Handle validation errors
                    state.validationErrors.forEach { error ->
                        if (error is ValidationResult.Error) {
                            when (error.inputField) {
                                InputField.FIRST_NAME -> binding.inputLayoutFarmerFirstName.error = error.errorMessage
                                InputField.LAST_NAME -> binding.inputLayoutFarmerLastName.error = error.errorMessage
                                InputField.PHONE_NUMBER -> binding.inputLayoutFarmerPhone.error = error.errorMessage
                                InputField.CROP_TYPE -> binding.inputLayoutCropType.error = error.errorMessage
                            }
                        }
                    }

                    // Display error message and return to FarmerActivity
                    state.successMessage?.getContentIfNotHandled()?.let { messageId ->
                        showSnackbar(messageId)
                        Handler(Looper.getMainLooper()).postDelayed({ finish() }, 1000)                    }
                }
            }
        }

    }

    fun setUpCropDropDown(){
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, CropList.validCropTypes)

        // this can be used if the list is large. so it searches after the first 3 characters
        binding.editTextCropType.threshold = 1
        binding.editTextCropType.setAdapter(adapter)

        binding.editTextCropType.setDropDownBackgroundResource(R.color.surfaceVariant)
    }

    fun showSnackbar(messageId: Int) {
        Snackbar.make(binding.root, getString(messageId), Snackbar.LENGTH_SHORT).show()
    }
}