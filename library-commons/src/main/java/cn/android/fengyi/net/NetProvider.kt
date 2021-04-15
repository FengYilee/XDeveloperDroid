package cn.android.fengyi.net

import okhttp3.CookieJar
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/11/24.
 */
class NetProvider {
    fun configIsRetryOnConnectionFailure(): Boolean {
        return true
    }

    fun configCommonHeaders(): Headers? {
        return null
    }

    fun configNetworkInterceptors(): Array<Interceptor>? {
        return null
    }

    fun configInterceptors(): Array<Interceptor>? {
        return null
    }

    fun configCookie(): CookieJar? {
        return null
    }

    fun configLogLevel(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    fun log(message: String?): Boolean = false

    fun configConnectTimeoutMills(): Long = 10000L

    fun configReadTimeoutMills(): Long = 2000000L

}