package com.kotlin.mvvm.kt.domain.usecases.getarticle

import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.data.repository.DataRepository
import com.kotlin.mvvm.kt.domain.models.cars.Car
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCarsUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend fun getCars(isOnline: Boolean): Flow<ResultData<List<Car>>> {
        return dataRepository.getCars(isOnline)
    }
}