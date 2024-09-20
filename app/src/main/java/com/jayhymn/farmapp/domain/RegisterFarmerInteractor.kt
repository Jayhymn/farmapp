package com.jayhymn.farmapp.domain

class RegisterFarmerInteractor(
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateCropTypeUseCase: ValidateCropTypeUseCase,
) {
    fun validateFarmerRegistration(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        cropType: String,
    ): List<ValidationResult> {
        val results = mutableListOf<ValidationResult>()

        results.add(validateFirstNameUseCase(firstName))
        results.add(validateLastNameUseCase(lastName))
        results.add(validatePhoneNumberUseCase(phoneNumber))
        results.add(validateCropTypeUseCase(cropType))

        return results.filterIsInstance<ValidationResult.Error>()
    }
}

