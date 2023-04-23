package com.example.weather.di.module

import androidx.viewbinding.BuildConfig
import com.example.weather.data.api.WeatherApi
import com.example.weather.di.qualifier.ApiInterceptor
import com.example.weather.di.qualifier.LangInterceptor
import com.example.weather.di.qualifier.LoggingInterceptor
import com.example.weather.di.qualifier.UnitsInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier


private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "73d4da5c56a53648c871a6ddcaa16254"
private const val QUERY_API_KEY = "appid"

private const val METRIC = "metric"
private const val QUERY_UNITS = "units"

private const val LANG_CODE = "en"
private const val QUERY_LANG = "lang"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @ApiInterceptor
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val newURL = original.url.newBuilder()
                .addQueryParameter(QUERY_API_KEY, API_KEY)
                .build()

            chain.proceed(
                original.newBuilder()
                    .url(newURL)
                    .build()
            )
        }
    }

    @Provides
    @UnitsInterceptor
    fun provideUnitsInterceptor(): Interceptor {
        return Interceptor{ chain ->
            val original = chain.request()
            val newURL = original.url.newBuilder()
                .addQueryParameter(QUERY_UNITS, METRIC)
                .build()

            chain.proceed(
                original.newBuilder()
                    .url(newURL)
                    .build()
            )
        }
    }

    @Provides
    @LangInterceptor
    fun provideLangInterceptor(): Interceptor {
        return Interceptor{ chain ->
            val original = chain.request()
            val newURL = original.url.newBuilder()
                .addQueryParameter(QUERY_LANG, LANG_CODE)
                .build()

            chain.proceed(
                original.newBuilder()
                    .url(newURL)
                    .build()
            )
        }
    }

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun provideOkhttp(
        @ApiInterceptor apiKeyInterceptor: Interceptor,
        @UnitsInterceptor unitsInterceptor: Interceptor,
        @LangInterceptor langInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .addInterceptor(langInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): WeatherApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .build()
            .create(WeatherApi::class.java)
}