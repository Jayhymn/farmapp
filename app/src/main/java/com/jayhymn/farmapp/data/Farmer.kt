package com.jayhymn.farmapp.data

import com.jayhymn.farmapp.ui.state.FarmerItemUiState

data class Farmer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val cropType: String,
) {
    fun toFarmerItemUiState(): FarmerItemUiState {
        return FarmerItemUiState(
            id = this.id,
            name = "${this.firstName} ${this.lastName}",
            cropType = this.cropType,
            phoneNumber = this.phoneNumber
        )
    }
}
