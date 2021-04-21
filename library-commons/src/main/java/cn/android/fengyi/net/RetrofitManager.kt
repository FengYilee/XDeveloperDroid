package cn.android.fengyi.net

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * Created by FengYi.Lee@hotmail.com> on 2020/11/24.
 */
internal class RetrofitManager private constructor() {
    fun getRetrofit(baseUrl: String): Retrofit {
        return this.getRetrofit(baseUrl, getOkHttpClient(NetProvider()), false)
    }

    fun getRetrofit(baseUrl: String, netProvider: NetProvider): Retrofit {
        return this.getRetrofit(baseUrl, getOkHttpClient(netProvider), false)
    }

    fun getRetrofit(baseUrl: String, netProvider: NetProvider, closeJson: Boolean): Retrofit {
        return this.getRetrofit(baseUrl, getOkHttpClient(netProvider), closeJson)
    }

    private fun getRetrofit(baseUrl: String, okHttpClient: OkHttpClient, closeJson: Boolean): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        if (!closeJson) {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        } else {
            builder.addConverterFactory(ScalarsConverterFactory.create())
        }

        return builder.build()
    }

    private fun getOkHttpClient(provider: NetProvider): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(provider.configConnectTimeoutMills(), TimeUnit.MILLISECONDS)
        builder.readTimeout(provider.configReadTimeoutMills(), TimeUnit.MILLISECONDS)
        builder.writeTimeout(provider.configReadTimeoutMills(), TimeUnit.MILLISECONDS)
        builder.proxy(Proxy.NO_PROXY)

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message) })
        loggingInterceptor.level = provider.configLogLevel()
        builder.addInterceptor(loggingInterceptor)

        if (provider.configInterceptors() != null) {
            provider.configInterceptors()?.let {
                configInterceptors->
                for (interceptor in configInterceptors) {
                    builder.addInterceptor(interceptor)
                }
            }

        }
        if (provider.configCommonHeaders() != null) {
            builder.addInterceptor { chain ->
                val builderHeader = chain.request().newBuilder()
                val commonHeaders = provider.configCommonHeaders()
                commonHeaders?.let {
                    headers->
                    for (i in 0 until headers.size()) {
                        builderHeader.addHeader(headers.name(i), headers.value(i))
                    }
                }

                val request = builderHeader.build()
                chain.proceed(request)
            }
        }
        return builder.build()
    }

    companion object {
        private var mInstance: RetrofitManager? = null
        @JvmStatic
        val instance: RetrofitManager?
            get() {
                if (mInstance == null) {
                    synchronized(RetrofitManager::class.java) { mInstance = RetrofitManager() }
                }
                return mInstance
            }
    }
}