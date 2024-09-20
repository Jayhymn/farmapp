package com.jayhymn.farmapp.ui.state


data class FarmersUiState(
    val farmers: List<FarmerItemUiState> = emptyList(),
    val errorMessages: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val successMessage: String? = null
)