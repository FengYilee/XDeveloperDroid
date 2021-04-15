package cn.android.fengyi.net


/**
 * Created by FengYi.Lee@hotmail.com> on 2020/11/24.
 */
object RetrofitHelper {
    fun <T> createApiService(clazz: Class<T>, baseUrl: String): T {
        return RetrofitManager.instance!!.getRetrofit(baseUrl).create(clazz)
    }

    fun <T> createApiService(clazz: Class<T>, baseUrl: String, netProvider: NetProvider): T {
        return RetrofitManager.instance!!.getRetrofit(baseUrl, netProvider).create(clazz)
    }

    fun <T> createApiService(clazz: Class<T>, baseUrl: String, netProvider: NetProvider, closeJson: Boolean): T {
        return RetrofitManager.instance!!.getRetrofit(baseUrl, netProvider, closeJson).create(clazz)
    }
}