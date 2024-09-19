package com.jayhymn.farmapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile


@Database(entities = [FarmerEntity::class], version = 1)
abstract class FarmerDb : RoomDatabase() {
    abstract fun farmerDao(): FarmerDao

    companion object {
//        @Volatile
//        private var INSTANCE: FarmerDb? = null
//
//        fun getDatabase(context: Context): FarmerDb {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    FarmerDb::class.java,
//                    "farmer_db"
//                ).build()
//
//                INSTANCE = instance
//                instance
//            }
//        }
    }
}
