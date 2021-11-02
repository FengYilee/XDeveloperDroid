package cn.android.fengyi.commons.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * Create by FengYi.Lee on 2021/10/21.
 * desc:
 */
object JsonFileUtils {

    /**
     * 解析json文件
     */
    fun getJson(fileName:String,context: Context):String{
        val stringBuilder = StringBuilder()
        val assetsManager = context.assets
        val bf = BufferedReader(InputStreamReader(assetsManager.open(fileName)))
        var line:String ?= ""
        while (line != null){
            line = bf.readLine()
            if (line != null){
                stringBuilder.append(line)
            }
        }
        return stringBuilder.toString()

    }


}