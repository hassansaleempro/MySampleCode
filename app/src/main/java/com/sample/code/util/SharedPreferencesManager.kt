package com.sample.code.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    companion object {
        private var instance: SharedPreferencesManager? = null

        fun getInstance(context: Context): SharedPreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesManager(context).also { instance = it }
            }
        }
    }

    // Methods for storing and retrieving string data
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun removeString(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearDB(){
        sharedPreferences.edit().clear().apply()
    }
}