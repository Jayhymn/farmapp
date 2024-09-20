package com.jayhymn.farmapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayhymn.farmapp.data.Farmer

@Dao
interface FarmerDao {
    @Query("SELECT * FROM farmers")
    suspend fun getAll(): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    suspend fun findByName(first: String, last: String): FarmerEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(farmer: FarmerEntity): Long

    @Delete
    suspend fun delete(farmer: FarmerEntity)
}