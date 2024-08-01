package com.sample.code.util.network

import android.util.Log
import com.sample.code.data.network.networkModels.ApiErrorResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


object ErrorUtils {
    fun parseError(apiError: String): ApiErrorResponse {
        Log.d("apierror",apiError)
        try {
            val json = JSONObject(apiError)
            var error = ApiErrorResponse(
                json.optInt("code", 0),
                json.optString("error", "")
            )

            if(error.message.contains("Fields"))
            {
                error= getProperErrorMessage(json)
            }
            return error
        }catch (ex: Exception){
            ex.printStackTrace()
            return ApiErrorResponse(
                0,
                ""
            )
        }
    }



    fun parseError(t: Throwable): ApiErrorResponse {
        try {
            return ApiErrorResponse(
                0,
                t.message!!
            )
        }catch (ex: Exception){
            ex.printStackTrace()
            return ApiErrorResponse(
                0,
                ""
            )
        }
    }


    private fun getProperErrorMessage(json:JSONObject): ApiErrorResponse
    {
        val jsonObject=json.getJSONObject("detail")
        val code=json.optInt("code", 0)
        var message=""
        val iter = jsonObject.keys()
        if(iter.hasNext()) {
            val key = iter.next()
            try {
                val value = jsonObject[key] as JSONArray
                message=value.getString(0)
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }
        return ApiErrorResponse(
            code,
            message
        )
    }
}