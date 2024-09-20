package com.jayhymn.farmapp.domain

class ValidatePhoneNumberUseCase {
    operator fun invoke(phone: String, ): ValidationResult {
        // Sanitize input by removing spaces, dashes, and parentheses
        var sanitizedPhone = phone.trim().replace(Regex("[^\\d+]"), "")

        // Check for country code starting with +234 or 234
        if (sanitizedPhone.startsWith("+234")) {
            sanitizedPhone = sanitizedPhone.removePrefix("+234")
        } else if (sanitizedPhone.startsWith("234")) {
            sanitizedPhone = sanitizedPhone.removePrefix("234")
        }

        // Ensure phone number starts with 0 or is exactly 10 digits after country code is removed
        return when {
            sanitizedPhone.isBlank() -> {
                ValidationResult.Error("Phone number cannot be empty", InputField.PHONE_NUMBER)
            }
            sanitizedPhone.length == 11 && sanitizedPhone.startsWith("0") -> {
                ValidationResult.Success
            }
            sanitizedPhone.length == 10 -> {
                ValidationResult.Success
            }
            else -> {
                ValidationResult.Error("Phone number format is incorrect", InputField.PHONE_NUMBER)
            }
        }
    }
}

