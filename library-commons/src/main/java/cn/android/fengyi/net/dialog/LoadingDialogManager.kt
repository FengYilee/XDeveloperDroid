package cn.android.fengyi.net.dialog

import android.app.Dialog

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/12/15.
 */
class LoadingDialogManager private constructor() {
    private var dialog: Dialog? = null
    fun setDialog(dialog: Dialog?) {
        this.dialog = dialog
    }

    fun show() {
        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
        }
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    companion object {
        private var loadDialogManager: LoadingDialogManager? = null
        val instance: LoadingDialogManager?
            get() {
                if (loadDialogManager == null) {
                    loadDialogManager = LoadingDialogManager()
                }
                return loadDialogManager
            }
    }

    init {
        LoadingDialogHelper.instance?.setOnShowDialogListener(object :
            LoadingDialogHelper.OnShowDialogListener {
            override fun onShow(isShow: Boolean, text: String?) {
                if (dialog != null) {
                    if (isShow) {
                        if (dialog is LoadingDialog) (dialog as LoadingDialog).show(text) else dialog!!.show()
                    } else {
                        dialog!!.dismiss()
                    }
                }
            }

            override fun onShow(isShow: Boolean) {
                if (dialog != null) {
                    if (isShow) {
                        dialog!!.show()
                    } else {
                        dialog!!.dismiss()
                    }
                }
            }

        })
    }
}