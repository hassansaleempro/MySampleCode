package com.sample.code.data.network

import com.sample.code.data.network.apiResponses.GeneralResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@JvmSuppressWildcards
interface RetrofitApi {

    // ****************************************** Auth Module Apis *****************************************************

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") userType: Int
    ): GeneralResponse


}