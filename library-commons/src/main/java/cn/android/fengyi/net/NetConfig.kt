package cn.android.fengyi.net

import cn.android.fengyi.commons.BuildConfig


object NetConfig {
    val host: String
        get() {
            return "$scheme://$ip:$port/"
        }

    val h5: String
        get() {
            return if (BuildConfig.DEBUG) {
                "http://106.38.32.80:18001/riskcontrol/riskinsh5/"
            } else {
                "$release_scheme://$release_ip:$release_port/"
            }
        }


    private const val debug_scheme = "http"
    private const val debug_ip = "106.38.32.80"
    private const val debug_port = 18001


    private const val release_scheme = "http"
    private const val release_ip = "157.122.153.115"
    private const val release_port = 7888

    val scheme by lazy {
        return@lazy if (BuildConfig.DEBUG) {
            debug_scheme
        } else {
            release_scheme
        }
    }

    val ip by lazy {
        return@lazy if (BuildConfig.DEBUG) {
            debug_ip
        } else {
            release_ip
        }
    }

    val port by lazy {
        return@lazy if (BuildConfig.DEBUG) {
            debug_port
        } else {
            release_port
        }
    }


}