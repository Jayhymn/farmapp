package com.jayhymn.farmapp.data.repositories

import com.jayhymn.farmapp.data.Farmer
import com.jayhymn.farmapp.data.db.FarmerDao
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
        cropType: String
    ){
    }

//    val farmers = Flow<Farmer>
//    farmers.catch{
//
//    }.collect{
//
//    }

    suspend fun getFarmerRecords(): List<Farmer> {
        withContext(Dispatchers.IO){
            return@withContext farmerDao.getAll().map { it.toFarmerData() }
        }
        return listOf()
    }
}