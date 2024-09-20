package com.jayhymn.farmapp.domain

sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Error(val errorMessage: String, val inputField: InputField) : ValidationResult
}

enum class InputField {
    FIRST_NAME,
    LAST_NAME,
    PHONE_NUMBER,
    CROP_TYPE
}