package cn.android.fengyi.net.observer

import io.reactivex.observers.DefaultObserver
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/11/24.
 */
abstract class FileDownLoadObserver<T> : DefaultObserver<T>() {
    override fun onNext(t: T) {
        onDownLoadSuccess(t)
    }

    override fun onError(e: Throwable) {
        onDownLoadFail(e)
    }

    //可以重写，具体可由子类实现
    override fun onComplete() {}

    //下载成功的回调
    abstract fun onDownLoadSuccess(t: T)

    //下载失败回调
    abstract fun onDownLoadFail(throwable: Throwable?)

    //下载进度监听
    abstract fun onProgress(progress: Int, total: Long)
    fun saveFile(responseBody: ResponseBody, destFileDir: String?, destFileName: String?): File {
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null
        val buf = ByteArray(2048)
        var len = 0
        var sum = 0
        val file = File(destFileDir)
        val outputFile = File(file, destFileName)
        try {
            inputStream = responseBody.byteStream()
            val total = responseBody.contentLength()
            if (!file.exists()) {
                file.mkdirs()
            }
            fos = FileOutputStream(outputFile)
            while (inputStream.read(buf).also { len = it } != -1) {
                sum += len
                fos.write(buf, 0, len)
                val finalSum = sum.toLong()
                onProgress((finalSum * 100 / total).toInt(), len.toLong())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return outputFile
    }
}