package com.sample.code.data.repository

import com.sample.code.data.network.apiResponses.GeneralResponse
import com.sample.code.data.network.RetrofitApi
import com.sample.code.util.network.ResultWrapper
import com.sample.code.util.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val retrofitApi: RetrofitApi
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login(
        userType: Int,
        email: String,
        password: String
    ): ResultWrapper<GeneralResponse> {
        return safeApiCall(dispatcher) {
            retrofitApi.login(
                email = email,
                password = password,
                userType = userType
            )
        }
    }


}