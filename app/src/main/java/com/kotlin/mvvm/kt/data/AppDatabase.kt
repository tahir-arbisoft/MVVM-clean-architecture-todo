package com.kotlin.mvvm.kt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kotlin.mvvm.kt.data.dao.CarDAO
import com.kotlin.mvvm.kt.domain.models.cars.Car
import com.kotlin.mvvm.kt.domain.models.cars.SubContentItemTypeConverter

/**
 * The Room database for this app
 */
@Database(
    entities = [Car::class],
    version = 1, exportSchema = false
)
@TypeConverters(SubContentItemTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCarListDao(): CarDAO

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "cars-db")
                .addCallback(object : RoomDatabase.Callback() {
                    //
                }).build()
        }
    }
}
