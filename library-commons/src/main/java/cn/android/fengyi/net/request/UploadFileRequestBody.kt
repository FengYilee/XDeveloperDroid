package cn.android.fengyi.net.request

import cn.android.fengyi.net.observer.FileUploadObserver
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.*
import java.io.File
import java.io.IOException

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/11/24.
 */
class UploadFileRequestBody(file: File, val fileUploadObserver: FileUploadObserver<ResponseBody>) : RequestBody() {
    private val mRequestBody: RequestBody = create(MediaType.parse("application/octet-stream"), file)
    private var bufferedSink: BufferedSink? = null

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mRequestBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return mRequestBody.contentType()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            val countingSink = CountingSink(sink)
            bufferedSink = Okio.buffer(countingSink)
        }
        //写入
        mRequestBody.writeTo(bufferedSink)
        //刷新
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink!!.flush()
    }

    inner class CountingSink(delegate: Sink?) : ForwardingSink(delegate) {
        //当前写入字节数
        var currentSize = 0L

        //总字节长度，避免多次调用contentLength()方法
        var totalSize = 0L

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            if (totalSize == 0L) {
                //获得contentLength的值，后续不再调用
                totalSize = contentLength()
            }
            //增加当前写入的字节数
            currentSize += byteCount
            //当前上传的百分比进度
            val progress = (currentSize * 100 / totalSize).toInt()
            fileUploadObserver.onProgressChange(progress.toLong(), contentLength())
        }
    }

}