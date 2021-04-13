package com.lfy.libretrofit

import com.lfy.libretrofit.RetrofitManager.Companion.instance

/**
 * Created by FengYi.Lee@hotmail.com> on 2020/11/24.
 */
object RetrofitHelper {
    fun <T> createApiService(clazz: Class<T>, baseUrl: String): T {
        return instance!!.getRetrofit(baseUrl).create(clazz)
    }

    fun <T> createApiService(clazz: Class<T>, baseUrl: String, netProvider: NetProvider): T {
        return instance!!.getRetrofit(baseUrl, netProvider).create(clazz)
    }

    fun <T> createApiService(clazz: Class<T>, baseUrl: String, netProvider: NetProvider, closeJson: Boolean): T {
        return instance!!.getRetrofit(baseUrl, netProvider, closeJson).create(clazz)
    }
}