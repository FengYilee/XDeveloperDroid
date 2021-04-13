package com.lfy.libretrofit.observer

import android.content.Context

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/11/24.
 */
abstract class FileUploadObserver<T> : DefaultObserver<T> {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, isShowDialog: Boolean) : super(context!!) {
        this.isShowDialog = isShowDialog
    }

    override fun onSuccess(t: T) {}

    override fun onNext(response: T) {
        onUpLoadSuccess(response)
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        onUpLoadFail(e)
    }

    fun onProgressChange(bytesWritten: Long, contentLength: Long) {
        onProgress((bytesWritten * 100 / contentLength).toInt())
    }

    //上传成功的回调
    abstract fun onUpLoadSuccess(t: T)

    //上传失败回调
    abstract fun onUpLoadFail(e: Throwable?)

    //上传进度回调
    abstract fun onProgress(progress: Int)
}