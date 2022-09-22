package com.kotlin.mvvm.kt.data.repository

import com.kotlin.mvvm.kt.data.dao.CarDAO
import com.kotlin.mvvm.kt.data.network.ApiService
import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.cars.Car
import com.kotlin.mvvm.kt.utility.constants.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val carDAO: CarDAO
) : DataRepositoryContract {

    override suspend fun getCars(connectivityAvailable: Boolean) = flow {
        emit(ResultData.Loading())
        when (connectivityAvailable) {
            true -> {
                emit(getRemoteCarsData())
            }
            false -> {
                emit(getLocalCarsData(connectivityAvailable))
            }
        }
    }.catch { emit(ResultData.Failed(it.message)) }
        .flowOn(Dispatchers.IO)

    override suspend fun getRemoteCarsData(): ResultData<List<Car>> {
        val response = apiService.getCars()
        if (response.status?.equals(Constants.STATUS_SUCCESS, true) == true) {
            insertCarDataInDb(response.content)
        }
        return getUpdatedCarsData()
    }

    override suspend fun getUpdatedCarsData(): ResultData<List<Car>> {
        return getLocalCarsData(true)
    }

    override suspend fun insertCarDataInDb(cars: List<Car>?) {
        cars?.let {
            carDAO.insertAll(it)
        }
    }

    override suspend fun getLocalCarsData(networkFlag: Boolean): ResultData<List<Car>> {
        val response = carDAO.getCars()
        return if (response.isNotEmpty()) {
            ResultData.Success(response)
        } else if (!networkFlag) {
            return ResultData.Failed(Constants.NO_NETWORK)
        } else {
            return ResultData.Failed(Constants.ZERO_RECORD)
        }
    }
}
