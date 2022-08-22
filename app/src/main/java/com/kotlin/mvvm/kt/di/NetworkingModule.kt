package com.kotlin.mvvm.kt.di


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotlin.mvvm.BuildConfig
import com.kotlin.mvvm.kt.data.network.ApiService
import com.kotlin.mvvm.kt.utility.constants.Constants
import com.kotlin.mvvm.kt.utility.utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun providesBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        val headerInterceptor = Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON)
                .header(Constants.CUSTOM_HEADER, Constants.CUSTOM_HEADER_VALUE)
                .method(original.method, original.body)
            if (BuildConfig.BUILD_TYPE.contains(Constants.PRODUCTION_BUILD)) requestBuilder.removeHeader(
                Constants.CUSTOM_HEADER
            )
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        if (!Constants.bypassSSL) {
            val okHttpClient = OkHttpClient().newBuilder()
            okHttpClient.addInterceptor(headerInterceptor)
            okHttpClient.connectTimeout(120, TimeUnit.SECONDS)
            okHttpClient.readTimeout(120, TimeUnit.SECONDS)
            okHttpClient.addInterceptor(loggingInterceptor)
            okHttpClient.build()
            return okHttpClient.build()
        }
        return utils.getUnPinnedSSLClient(loggingInterceptor, headerInterceptor)
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory).addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }

    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}