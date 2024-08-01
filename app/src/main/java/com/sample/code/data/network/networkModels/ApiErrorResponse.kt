package com.sample.code.data.network.networkModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiErrorResponse (
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val message: String
): Serializable