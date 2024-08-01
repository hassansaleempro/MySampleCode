package com.sample.code.util.di

import android.content.Context
import com.sample.code.data.network.RetrofitApi
import com.casttypes.app.utils.di.AuthInterceptor
import com.sample.code.util.LoadingDialog
import com.sample.code.util.MyConstants
import com.sample.code.util.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val timeout = 30L // Timeout in seconds

    // creating Gson Instance
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            // Add other OkHttpClient configurations if needed
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(@ApplicationContext context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }

    // creating Retrofit Instance
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MyConstants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RetrofitApi =
        retrofit.create(RetrofitApi::class.java)

    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideLoadingDialog(@ApplicationContext context: Context): LoadingDialog {
        return LoadingDialog(context)
    }
}
