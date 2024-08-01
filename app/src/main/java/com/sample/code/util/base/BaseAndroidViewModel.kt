package com.sample.code.util.base

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.casttypes.app.models.networkModels.BaseResponse
import com.sample.code.util.MyConstants
import com.sample.code.util.OneShotEvent
import com.sample.code.util.network.ResultWrapper

open class BaseAndroidViewModel : ViewModel() {

    val snackBarMessage = MutableLiveData<OneShotEvent<String>>()
    val progressBar = MutableLiveData<OneShotEvent<Boolean>>()

    var serverResponseLiveData: MutableLiveData<OneShotEvent<BaseResponse>> =
        MutableLiveData()

    var serverResponseLiveData2: MutableLiveData<OneShotEvent<BaseResponse>> =
        MutableLiveData()



    private fun showSnackbarMessage(message: String) {
//        snackBarMessage.value = OneShotEvent(message)
        serverResponseLiveData.value = OneShotEvent(
            BaseResponse(
                404,
                "",
                message
            )
        )
    }

    private fun showNetworkError(message: String) {
        serverResponseLiveData.value = OneShotEvent(
            BaseResponse(
                404,
                "",
                message
            )
        )
    }

    protected fun handleErrorType(error: ResultWrapper<Any>) {
        when (error) {
            is ResultWrapper.NetworkError ->
                showNetworkError(error.toString())

            is ResultWrapper.GenericError -> {
                if (error.code != 400) {
                    showSnackbarMessage(error.error?.message.toString())
                }
            }

            else -> Unit
        }
    }

    protected fun showToast(context: Context, message: String?) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    protected fun handleErrorTypeFilmmaker(context: Context, error: ResultWrapper<Any>) {
        when (error) {
            is ResultWrapper.NetworkError -> {
                showToast(context, error.toString())
                Log.d(MyConstants.API_LOGS_TAG, error.toString())
            }

            is ResultWrapper.GenericError -> {
                showToast(context, error.error?.message.toString())
                Log.d(MyConstants.API_LOGS_TAG, error.error?.message.toString())
            }


            else -> Unit
        }
    }


    fun showProgressBar(show: Boolean) {
        progressBar.value = OneShotEvent(show)
    }


}