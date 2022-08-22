package com.kotlin.mvvm.kt.utility

import com.kotlin.mvvm.kt.utility.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.lang.RuntimeException
import java.security.KeyStore
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.net.ssl.HostnameVerifier

class utils {
    companion object{
        fun getUnPinnedSSLClient(
            loggingInterceptor: HttpLoggingInterceptor,
            headersInterceptor: Interceptor
        ): OkHttpClient {
            return try {
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                check(!(trustAllCerts.size != 1 || trustAllCerts[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + Arrays.toString(
                        trustAllCerts
                    )
                }
                val trustManager = trustAllCerts[0] as X509TrustManager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
                loggingInterceptor.let {
                    headersInterceptor.let { it1 ->
                        OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.socketFactory, trustManager)
                            .addInterceptor(it)
                            .addInterceptor(it1)
                            .readTimeout(Constants.TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .connectTimeout(Constants.TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
                            .build()
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    }

}