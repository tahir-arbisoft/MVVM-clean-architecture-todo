package com.kotlin.mvvm.kt.data.repository

import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.cars.Car
import kotlinx.coroutines.flow.Flow

interface DataRepositoryContract {

    suspend fun getCars(connectivityAvailable: Boolean): Flow<ResultData<List<Car>>>
    suspend fun getRemoteCarsData(): ResultData<List<Car>>
    suspend fun getLocalCarsData(networkFlag: Boolean): ResultData<List<Car>>
    suspend fun insertCarDataInDb(cars: List<Car>?)
    suspend fun getUpdatedCarsData(): ResultData<List<Car>>
}