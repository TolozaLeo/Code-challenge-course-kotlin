package com.cursokotlin.codechallenge.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.cursokotlin.codechallenge.data.service.AvengersApiService
import com.cursokotlin.codechallenge.data.service.security.AvengersHasKeyBuilder
import com.cursokotlin.codechallenge.data.service.security.HashKeyBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: BaseUrl, moshi: Moshi): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .baseUrl(baseUrl.url!!)
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(application: Application): BaseUrl {
        val applicationInfo: ApplicationInfo = application.packageManager
            .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        return BaseUrl(applicationInfo.metaData.getString("BASE_URL"))
    }

    @Provides
    @Singleton
    fun provideAvengersApiService(retrofit: Retrofit): AvengersApiService {
        return retrofit.create(AvengersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHashKeyBuilder(): HashKeyBuilder = AvengersHasKeyBuilder()

    data class BaseUrl(
        val url: String?
    )

    companion object {
        private const val SHARED_PREFERENCES_KEY = "app_preference"
    }
}