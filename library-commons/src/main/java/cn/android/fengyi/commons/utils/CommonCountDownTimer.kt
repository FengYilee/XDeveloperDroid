package cn.android.fengyi.commons.utils

import android.os.CountDownTimer

/**
 * 公共倒计时类
 */
open class CommonCountDownTimer(millisInFuture:Long,countDownInterval:Long): CountDownTimer(millisInFuture,countDownInterval) {

    private var countDownTimerListener: OnCountDownTimerListener?= null

    override fun onTick(millisUntilFinished: Long) {
        if (null != countDownTimerListener) {
            countDownTimerListener?.onTick(millisUntilFinished);
        }
    }

    override fun onFinish() {
        if (null != countDownTimerListener){
            countDownTimerListener?.onFinish();
        }
    }

    interface OnCountDownTimerListener{
        /**
         * 更新倒计时时间
         *
         * @param millisUntilFinished
         */
        fun onTick(millisUntilFinished:Long );

        /**
         * 完成倒计时
         */
        fun onFinish()
    }
}