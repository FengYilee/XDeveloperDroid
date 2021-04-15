package cn.android.fengyi.net.dialog

import android.text.TextUtils

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/12/15.
 */
class LoadingDialogHelper {
    private var count = 0
    private var listener: OnShowDialogListener? = null
    fun down() {
        count--
        if (count < 0) count = 0
        listener?.run {
            onShow(isShow)
        }
    }

    @JvmOverloads
    fun up(text: String? = "") {
        count++
        if (listener != null) {
            if (TextUtils.isEmpty(text)) listener!!.onShow(isShow) else listener!!.onShow(isShow, text)
        }
    }

    fun showDialog() {
        up()
    }

    fun showDialog(text: String?) {
        up(text)
    }

    fun cancelDialog() {
        down()
    }

    fun rest() {
        count = 0
        if (listener != null) {
            listener!!.onShow(isShow)
        }
    }

    private val isShow: Boolean get() = count != 0

    fun setOnShowDialogListener(listener: OnShowDialogListener?) {
        this.listener = listener
    }

    interface OnShowDialogListener {
        fun onShow(isShow: Boolean)
        fun onShow(isShow: Boolean, text: String?)
    }

    companion object {
        private var loadDialogManager: LoadingDialogHelper? = null
        val instance: LoadingDialogHelper?
            get() {
                if (loadDialogManager == null) {
                    loadDialogManager = LoadingDialogHelper()
                }
                return loadDialogManager
            }
    }
}