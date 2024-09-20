package com.jayhymn.farmapp.domain

class ValidateFirstNameUseCase {

    operator fun invoke(name: String): ValidationResult {
        // Sanitize input by trimming and removing potentially malicious characters.
        val sanitizedInput = sanitizeInput(name)

        return when {
            sanitizedInput.isBlank() -> {
                ValidationResult.Error("First Name cannot be empty", InputField.FIRST_NAME)
            }
            sanitizedInput.length < 3 -> {
                ValidationResult.Error("First Name must be at least 3 characters", InputField.FIRST_NAME)
            }
            // You can add more checks as needed.
            else -> {
                ValidationResult.Success
            }
        }
    }

    // Basic sanitization to avoid common SQL injection or script injection
    private fun sanitizeInput(input: String): String {
        return input.trim().replace(Regex("[^a-zA-Z0-9\\s]"), "")
    }
}
