package com.jayhymn.farmapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jayhymn.farmapp.data.Farmer
import com.jayhymn.farmapp.utils.DateUtils

@Entity(tableName = "Farmers")
data class FarmerEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    @ColumnInfo(name = "crop_type")
    val cropType: String?,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: String = DateUtils.getCurrentTimestamp(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: String = DateUtils.getCurrentTimestamp(),
){
    fun toFarmerData(): Farmer {
        return Farmer(
            id = this.uid,
            firstName = this.firstName ?: "",
            lastName = this.lastName ?: "",
            cropType = this.cropType ?: "",
            phoneNumber = this.phoneNumber ?: "",
            )
    }
}