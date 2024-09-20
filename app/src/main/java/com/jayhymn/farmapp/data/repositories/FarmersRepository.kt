package com.jayhymn.farmapp.data.repositories

import com.jayhymn.farmapp.data.Farmer
import com.jayhymn.farmapp.data.db.FarmerDao
import com.jayhymn.farmapp.data.db.FarmerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FarmersRepository @Inject constructor(
    private val farmerDao: FarmerDao,
//    apiSource: ApiDataSource
) {

    suspend fun createFarmer(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        cropType: String
    ): Long {
        return farmerDao.insert(
            FarmerEntity(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                cropType = cropType
            )
        )
    }

    suspend fun getFarmerRecords(): List<Farmer> = farmerDao.getAll().map { it.toFarmerData() }
}