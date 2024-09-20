package com.jayhymn.farmapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayhymn.farmapp.Event
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.data.db.FarmerDao
import com.jayhymn.farmapp.data.repositories.FarmersRepository
import com.jayhymn.farmapp.domain.RegisterFarmerInteractor
import com.jayhymn.farmapp.domain.ValidationResult
import com.jayhymn.farmapp.ui.state.FarmerRegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FarmerRegistrationViewModel @Inject constructor (
    private val registerFarmerInteractor: RegisterFarmerInteractor,
    private val farmersRepo: FarmersRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FarmerRegisterUiState())
    val state = _uiState.asStateFlow()

    fun onRegisterButtonClicked(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        cropType: String
    ) {
        _uiState.update { it.copy(isLoading = true) }
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
            _uiState.update { state -> state.copy(validationErrors = errors, isLoading = false)
            }
        }
    }

    private fun registerFarmer(firstName: String, lastName: String, phoneNumber: String, cropType: String) {
        viewModelScope.launch {
            try {
                val record: Long = farmersRepo.createFarmer(firstName, lastName, phoneNumber, cropType)

                _uiState.update {
                    if (record > 0) {
                        it.copy(successMessage = Event(R.string.success_message.toString()), isLoading = false)
                    } else {
                        // If the phone number has been used before, it will ignore the operation and return 0 from the database
                        it.copy(error = Event(R.string.already_exist_message.toString()), isLoading = false)
                    }
                }
            } catch (io: IOException) {
                _uiState.update { it.copy(error = Event(R.string.error_message.toString()), isLoading = false) }
            }
        }
    }

    private fun clearValidationErrors() {
        _uiState.update { it.copy(validationErrors = emptyList()) }
    }
}
