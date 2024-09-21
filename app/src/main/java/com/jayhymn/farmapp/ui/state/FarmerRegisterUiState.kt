package com.jayhymn.farmapp.ui.state

import com.jayhymn.farmapp.Event
import com.jayhymn.farmapp.domain.ValidationResult

data class FarmerRegisterUiState(
    val farmers: List<FarmerItemUiState> = emptyList(),
    val validationErrors: List<ValidationResult> = emptyList(),
    val error: Event<Int>? = null,
    val isLoading: Boolean = false,
    val successMessage: Event<Int>? = null
)