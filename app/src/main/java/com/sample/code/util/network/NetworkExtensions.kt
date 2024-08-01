package com.sample.code.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.sample.code.data.network.networkModels.ApiErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }

                else -> {
                    ResultWrapper.GenericError(
                        0,
                        ApiErrorResponse(
                            0,
                            "" + throwable.message
                        )
                    )
                }
            }
        }
    }
}


private fun convertErrorBody(throwable: HttpException): ApiErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.let {
            return ErrorUtils.parseError(it.string())
        }
    } catch (exception: Exception) {
        ApiErrorResponse(
            throwable.code(),
            throwable.message()
        )
    }
}


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        @Suppress("DEPRECATION")
        connectivityManager.activeNetworkInfo?.run {
            when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }

    return false
}