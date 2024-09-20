package com.jayhymn.farmapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jayhymn.farmapp.domain.RegisterFarmerInteractor
import com.jayhymn.farmapp.domain.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FarmerRegistrationViewModel @Inject constructor (
    private val registerFarmerInteractor: RegisterFarmerInteractor
) : ViewModel() {

    private val _validationErrors = MutableStateFlow<List<ValidationResult>>(emptyList())
    val validationErrors = _validationErrors.asStateFlow()

    fun onRegisterButtonClicked(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        cropType: String
    ) {
        val errors = registerFarmerInteractor.validateFarmerRegistration(
            firstName,
            lastName,
            phoneNumber,
            cropType
        )
        if (errors.isEmpty()) {
            // All validations passed, proceed with registration logic
            registerFarmer(firstName, lastName, phoneNumber, cropType)
        } else {
            _validationErrors.value = errors
        }
    }

    private fun registerFarmer(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        cropType: String
    ) {

    }
}
