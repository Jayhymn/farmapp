package com.jayhymn.farmapp.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RegisterFarmerInteractorTest {

    private lateinit var validateFirstNameUseCase: ValidateFirstNameUseCase
    private lateinit var validateLastNameUseCase: ValidateLastNameUseCase
    private lateinit var validatePhoneNumberUseCase: ValidatePhoneNumberUseCase
    private lateinit var validateCropTypeUseCase: ValidateCropTypeUseCase
    private lateinit var registerFarmerInteractor: RegisterFarmerInteractor

    @Before
    fun setup() {
        validateFirstNameUseCase = mock(ValidateFirstNameUseCase::class.java)
        validateLastNameUseCase = mock(ValidateLastNameUseCase::class.java)
        validatePhoneNumberUseCase = mock(ValidatePhoneNumberUseCase::class.java)
        validateCropTypeUseCase = mock(ValidateCropTypeUseCase::class.java)

        registerFarmerInteractor = RegisterFarmerInteractor(
            validateFirstNameUseCase,
            validateLastNameUseCase,
            validatePhoneNumberUseCase,
            validateCropTypeUseCase
        )
    }

    @Test
    fun `validateFarmerRegistration returns no errors for valid inputs`() {
        // Arrange
        val firstName = "John"
        val lastName = "Doe"
        val phoneNumber = "08123456789"
        val cropType = "Maize"

        `when`(validateFirstNameUseCase(firstName)).thenReturn(ValidationResult.Success)
        `when`(validateLastNameUseCase(lastName)).thenReturn(ValidationResult.Success)
        `when`(validatePhoneNumberUseCase(phoneNumber)).thenReturn(ValidationResult.Success)
        `when`(validateCropTypeUseCase(cropType)).thenReturn(ValidationResult.Success)

        // Act
        val result = registerFarmerInteractor.validateFarmerRegistration(firstName, lastName, phoneNumber, cropType)

        // Assert
        assertEquals(emptyList<ValidationResult>(), result)
    }

    @Test
    fun `validateFarmerRegistration returns validation errors for invalid inputs`() {
        // Arrange
        val firstName = "Jo"
        val lastName = ""
        val phoneNumber = "invalid_phone"
        val cropType = "UnknownCrop"

        val firstNameError = ValidationResult.Error("First name too short", InputField.FIRST_NAME)
        val lastNameError = ValidationResult.Error("Last name is required", InputField.LAST_NAME)
        val phoneNumberError = ValidationResult.Error("Invalid phone number", InputField.PHONE_NUMBER)
        val cropTypeError = ValidationResult.Error("Crop type not recognized", InputField.CROP_TYPE)

        `when`(validateFirstNameUseCase(firstName)).thenReturn(firstNameError)
        `when`(validateLastNameUseCase(lastName)).thenReturn(lastNameError)
        `when`(validatePhoneNumberUseCase(phoneNumber)).thenReturn(phoneNumberError)
        `when`(validateCropTypeUseCase(cropType)).thenReturn(cropTypeError)

        // Act
        val result = registerFarmerInteractor.validateFarmerRegistration(firstName, lastName, phoneNumber, cropType)

        // Assert
        assertEquals(listOf(firstNameError, lastNameError, phoneNumberError, cropTypeError), result)
    }
}
