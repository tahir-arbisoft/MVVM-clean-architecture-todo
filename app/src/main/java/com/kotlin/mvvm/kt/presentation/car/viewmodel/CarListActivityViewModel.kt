package com.kotlin.mvvm.kt.presentation.car.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.cars.Car
import com.kotlin.mvvm.kt.domain.usecases.getarticle.GetCarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListActivityViewModel @Inject constructor(
    private val useCase: GetCarsUseCase
) : ViewModel() {

    private var mutableCarResponse = MutableLiveData<ResultData<List<Car?>>>()
    val carResponse: LiveData<ResultData<List<Car?>>>
        get() = mutableCarResponse

    fun getCars(isNetworkAvailable: Boolean) = viewModelScope.launch {
        useCase.getCars(isNetworkAvailable).collect {
            mutableCarResponse.postValue(it)
        }
    }
}
