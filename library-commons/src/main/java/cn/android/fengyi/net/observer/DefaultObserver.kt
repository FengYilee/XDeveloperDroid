package cn.android.fengyi.net.observer

import android.content.Context
import android.widget.Toast
import cn.android.fengyi.net.dialog.LoadingDialogHelper
import cn.android.fengyi.net.exception.ServerResponseException
import com.google.gson.JsonParseException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.observers.DisposableObserver
import org.json.JSONException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/11/24.
 */
abstract class DefaultObserver<T> : DisposableObserver<T> {
    var mContext: Context
    @JvmField
    protected var isShowDialog = true
    private var dialogText = "请稍候..."

    constructor(context: Context) {
        mContext = context
    }

    constructor(context: Context, dialogText: String) {
        mContext = context
        this.dialogText = dialogText
    }

    /**
     * 是否打开加载框
     * @param isShowDialog  默认true
     */
    constructor(context: Context, isShowDialog: Boolean) {
        mContext = context
        this.isShowDialog = isShowDialog
    }

    override fun onNext(response: T) {
        onSuccess(response)
        onFinish()
    }

    override fun onError(e: Throwable) {
        if (isShowDialog) LoadingDialogHelper.instance?.cancelDialog()
        if (e is HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK)
        } else if (e is ConnectException
                || e is UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR)
        } else if (e is InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT)
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
                || e is IllegalStateException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR)
        } else if (e is ServerResponseException) {
            onFail(e.message)
        } else {
            onFail(e.message)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isShowDialog) LoadingDialogHelper.instance?.showDialog(dialogText)
    }

    override fun onComplete() {
        if (isShowDialog) LoadingDialogHelper.instance?.cancelDialog()
    }

    abstract fun onSuccess(t: T)

    fun onFinish() {

    }

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    private fun onFail(message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private fun onException(reason: ExceptionReason?) {
        when (reason) {
            ExceptionReason.CONNECT_ERROR -> Toast.makeText(mContext, "网络开了点小差,请检查您的网络。", Toast.LENGTH_LONG).show()
            ExceptionReason.CONNECT_TIMEOUT -> Toast.makeText(mContext, "与服务连接超时。", Toast.LENGTH_LONG).show()
            ExceptionReason.BAD_NETWORK -> Toast.makeText(mContext, "请求错误,请检查是否404。", Toast.LENGTH_LONG).show()
            ExceptionReason.PARSE_ERROR -> Toast.makeText(mContext, "解析错误,请检查json串是否正确", Toast.LENGTH_LONG).show()
            ExceptionReason.UNKNOWN_ERROR -> Toast.makeText(mContext, "未知错误,请查看错误日志.", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(mContext, "未知错误,请查看错误日志.", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 请求网络失败原因
     */
    enum class ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,

        /**
         * 网络问题
         */
        BAD_NETWORK,

        /**
         * 连接错误
         */
        CONNECT_ERROR,

        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,

        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }
}