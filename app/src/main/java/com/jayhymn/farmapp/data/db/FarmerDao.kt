package com.jayhymn.farmapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jayhymn.farmapp.data.Farmer

@Dao
interface FarmerDao {
    @Query("SELECT * FROM farmers")
    fun getAll(): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): FarmerEntity

    @Insert
    fun insertAll(vararg farmer: FarmerEntity)

    @Delete
    fun delete(farmer: FarmerEntity)
}