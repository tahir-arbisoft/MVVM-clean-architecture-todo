package com.kotlin.mvvm.kt.utility

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateFormat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.mvvm.kt.utility.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.lang.RuntimeException
import java.security.KeyStore
import java.security.cert.X509Certificate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class AppUtils {
    companion object {
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

        fun loadImage(mContext: Context, imageURL: String?, imageView: ImageView) {
            if (!imageURL.isNullOrEmpty())
                Glide.with(mContext).load(imageURL).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(false).into(imageView)
        }

        fun convertToDateFormat(dateString: String, context: Context): String {
            var returnDate = ""
            val dateArray = dateString.split(".").toTypedArray()
            dateArray.let {
                if (it.size > 2) {
                    val yearTime = dateArray[2]
                    val yearTimeArray = yearTime.split(" ").toTypedArray()
                    if (yearTimeArray.size > 1) {
                        val year = yearTimeArray[0]
                        var time = yearTimeArray[1]
                        val day = it[0]
                        val month = getCalenderMonth(it[1].toInt())
                        time = if (is24HourFormat(context)) {
                            get24HourFormatTime(time).toString()
                        } else {
                            get12HourFormatTime(time).toString()
                        }
                        returnDate = if (isCurrentYearDate(year)) {
                            // current year  29.11.2017 15:05  : “[day] [month], [time]”. For example: “16 March, 12:34”
                            "$returnDate$day $month , $time"
                        } else {
                            // “[day] [month] [year], [time]”. For example: “20 December 2017, 21:43”
                            "$returnDate$day $month $year, $time"
                        }
                    }
                }
            }
            return returnDate
        }

        @SuppressLint("SimpleDateFormat")
        fun getCalenderMonth(index: Int): String {
            val calendar: Calendar = Calendar.getInstance()
            val month = SimpleDateFormat("MMMM")
            calendar[Calendar.MONTH] = index - 1
            return month.format(calendar.time)
        }

        private fun is24HourFormat(context: Context): Boolean {
            return DateFormat.is24HourFormat(context)
        }

        private fun isCurrentYearDate(year: String): Boolean {
            val calendar: Calendar = Calendar.getInstance()
            return (calendar.get(Calendar.YEAR).toString() == year)
        }

        @SuppressLint("SimpleDateFormat")
        private fun get12HourFormatTime(time: String): String? {
            try {
                val sdf = SimpleDateFormat("H:mm")
                val dateObj = sdf.parse(time)
                return (dateObj?.let { SimpleDateFormat("K:mm a").format(it) })
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        @SuppressLint("SimpleDateFormat")
        private fun get24HourFormatTime(time: String): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj = sdf.parse(time)
                return (dateObj?.let { sdf.format(it) })
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        fun isOnline(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }
    }


}