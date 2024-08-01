package com.sample.code.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.code.data.network.apiResponses.GeneralResponse
import com.sample.code.data.repository.AuthRepository
import com.sample.code.util.base.BaseAndroidViewModel
import com.sample.code.util.network.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseAndroidViewModel() {

    var loginResponseLiveData: MutableLiveData<GeneralResponse?> =
        MutableLiveData()

    fun login(
        context: Context,
        email: String,
        password: String,
        userType: Int,
    ) {
        showProgressBar(true)
        viewModelScope.launch {
            authRepository.login(
                email = email,
                password = password,
                userType = userType
            ).let { response ->

                showProgressBar(false)

                when (response) {
                    is ResultWrapper.Success ->
                        if (response.value.code == 200) {
                            loginResponseLiveData.value = response.value
                        } else {
                            showToast(context, response.value.message)
                        }

                    else -> handleErrorTypeFilmmaker(context, response)
                }
            }
        }
    }

}