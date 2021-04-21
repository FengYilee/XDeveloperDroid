package cn.android.fengyi.net

import okhttp3.CookieJar
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by FengYi.Lee<fengyi.li> on 2020/11/24.
 */
open class NetProvider {

    open fun configIsRetryOnConnectionFailure(): Boolean {
        return true
    }

    open fun configCommonHeaders(): Headers? {
        return null
    }

    open fun configNetworkInterceptors(): Array<Interceptor>? {
        return null
    }

    open fun configInterceptors(): Array<Interceptor>? {
        return null
    }

    open fun configCookie(): CookieJar? {
        return null
    }

    open fun configLogLevel(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    open fun log(message: String?): Boolean = false

    open fun configConnectTimeoutMills(): Long = 10000L

    open fun configReadTimeoutMills(): Long = 2000000L

}