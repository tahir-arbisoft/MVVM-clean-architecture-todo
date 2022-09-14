package com.kotlin.mvvm.kt.data.network

import com.sevenpeakssoftware.zain.domain.models.getCars.GetCarResponse
import retrofit2.http.GET

interface ApiService {

    @GET(NetworkingConstants.GET_ARTICLES)
    suspend fun getCars(): GetCarResponse
}