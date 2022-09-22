package com.kotlin.mvvm.kt.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.mvvm.kt.domain.models.cars.Car

@Dao
interface CarDAO {

    @Query("Select * from Car")
    suspend fun getCars(): List<Car>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(carList: List<Car>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car: Car)

}