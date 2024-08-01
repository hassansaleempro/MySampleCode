package com.sample.code.data.network.apiResponses

import com.google.gson.annotations.SerializedName
import com.sample.code.data.network.apiResponses.SuccessData

data class GeneralResponse(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("data") var data: SuccessData? = SuccessData(),
    @SerializedName("message") var message: String? = null,
    @SerializedName("success") var isBookmarked: Boolean? = null,
)