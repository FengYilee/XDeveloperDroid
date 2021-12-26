package cn.android.fengyi.net

import cn.android.fengyi.commons.BuildConfig
import okhttp3.Headers
import okhttp3.logging.HttpLoggingInterceptor

object HttpApi {

    private var apiService: ApiService? = null

    fun getApiService(): ApiService {
        if (apiService == null) {
            apiService = RetrofitHelper.createApiService(
                ApiService::class.java,
                BuildConfig.API_HOST,
                provider()
            )
        }
        return apiService!!
    }

    private fun provider() = object : NetProvider() {

        override fun configCommonHeaders(): Headers? {
            return Headers.Builder()
                .add("Content-Type", "application/json;charset=utf-8")
                .build()
        }

        override fun configLogLevel(): HttpLoggingInterceptor.Level {
            return HttpLoggingInterceptor.Level.BODY
        }

    }
}