package com.lfy.components.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.lfy.components.R
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

/**
 * Create by FengYi.Lee on 2021/10/20.
 * desc:
 */
object DialogUtils {

    /**
     * 底部弹出选择对话框
     */
    fun showBottomDialog(context: Context) {
        val bottomDialog = Dialog(context, R.style.bottom_dialog)
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_content, null)
        bottomDialog.setContentView(contentView)
        val layoutParams = contentView.layoutParams
        layoutParams.width = context.resources.displayMetrics.widthPixels
        contentView.layoutParams = layoutParams
        bottomDialog.window?.apply {
            setGravity(Gravity.BOTTOM)
            setWindowAnimations(R.style.BottomDialog_Animation)
        }
        bottomDialog.show()
    }

    /**
     * 日期选择器（底部弹出）
     */
    fun showCardDateTimePicker(context: Context,title:String): CardDatePickerDialog.Builder {
        return CardDatePickerDialog.builder(context)
            .setTitle(title)
            .showDateLabel(false)
            .showBackNow(false)
            .showFocusDateInfo(false)
            .setDisplayType(
                intArrayOf(
                    DateTimeConfig.YEAR,
                    DateTimeConfig.MONTH,
                    DateTimeConfig.DAY
                ).toMutableList()
            )
    }

    fun showSelect2OneDialog(context: Context,title:String,items:Array<String>,listener:DialogInterface.OnClickListener):AlertDialog{
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(title)
            .setItems(items,listener)
        return dialogBuilder.create()
    }


}