package com.jayhymn.farmapp.domain

import com.jayhymn.farmapp.CropList

class ValidateCropTypeUseCase {
    operator fun invoke(cropType: String?): ValidationResult {
        CropList.validCropTypes
        return when {
            cropType.isNullOrBlank() -> {
                ValidationResult.Error("Crop type cannot be empty", InputField.CROP_TYPE)
            }
            !CropList.validCropTypes.contains(cropType) -> {
                ValidationResult.Error("Invalid crop type. Please select from the list.", InputField.CROP_TYPE)
            }
            else -> {
                ValidationResult.Success
            }
        }
    }
}

