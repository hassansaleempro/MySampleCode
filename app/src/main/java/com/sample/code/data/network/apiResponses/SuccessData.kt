package com.sample.code.data.network.apiResponses

import com.sample.code.data.network.apiResponses.models.UserData
import com.google.gson.annotations.SerializedName

data class SuccessData(
    //Before Auth Response
    @SerializedName("access_token" ) var accessToken : String? = null,
    @SerializedName("token_type"   ) var tokenType   : String? = null,
    @SerializedName("user"         ) var user        : UserData?   = UserData(),

    )