package com.casttypes.app.utils.di

import android.content.Context
import android.util.Log
import com.sample.code.util.MyConstants
import com.sample.code.util.SharedPreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor@Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {
    private var sharedPreferencesManager = SharedPreferencesManager.getInstance(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        try {
            // If token has been saved, add it to the request
            // If token has been saved, add it to the request

            val userJsonString = sharedPreferencesManager.getString(MyConstants.ACCESS_TOKEN, "")
//            val userJsonString = "10|oKzydL2wNFOYV5bWVNgEeppjThw0WZWzfHTMIgDi999cd62b"
            if (userJsonString != null) {
                if (userJsonString.isNotEmpty()) {

                    requestBuilder.addHeader(
                        "Authorization",
                        "Bearer $userJsonString"
                    )
                    Log.d("access_token", "Bearer $userJsonString")
                } else {
                    // Handle the case when there is no saved user data
                }
            }
            return chain.proceed(requestBuilder.build())
        } catch (e: Exception) {
            Log.d("Authorization", e.message.toString())
        }
        return chain.proceed(requestBuilder.build())
    }
}