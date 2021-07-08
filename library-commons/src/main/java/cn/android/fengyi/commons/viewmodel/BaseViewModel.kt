package cn.android.fengyi.commons.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
import cn.android.fengyi.commons.BaseConstants
import cn.android.fengyi.commons.SingleLiveEvent
import java.util.*
import kotlin.collections.HashMap

abstract class BaseViewModel : ViewModel(), ViewModelLifecycle, ViewBehavior {

    //loading视图显示Event
    var loadingEvent = SingleLiveEvent<Boolean>()
        private set

    //无数据视图显示Event
    var emptyPageEvent = SingleLiveEvent<Boolean>()
        private set

    //toast提示Event
    var toastEvent = SingleLiveEvent<Map<String, *>>()
        private set

    // 不带参数的页面跳转Event
    var pageNavigationEvent = SingleLiveEvent<Any>()
        private set

    // 点击系统返回键Event
    var backPressEvent = SingleLiveEvent<Any?>()
        private set

    // 关闭页面Event
    var finishPageEvent = SingleLiveEvent<Any?>()
        private set

    private lateinit var lifecycleOwner: LifecycleOwner


    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifecycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    override fun showLoadingUI(isShow: Boolean) {
        loadingEvent.postValue(isShow)
    }

    override fun showEmptyUI(isShow: Boolean) {
        emptyPageEvent.postValue(isShow)
    }

    override fun showToast(map: Map<String, *>) {
        toastEvent.postValue(map)
    }

    override fun navigate(page: Any?) {
        pageNavigationEvent.postValue(page)
    }

    override fun backPress(arg: Any?) {
        backPressEvent.postValue(arg)
    }

    override fun finishPage(arg: Any?) {
        finishPageEvent.postValue(arg)
    }

    protected fun showToast(str:String){
        showToast(str,null)
    }


    private fun showToast(str: String, duration: Int?) {
        val map = HashMap<String, Any>().apply {
            put(
                    BaseConstants.TOAST_KEY_CONTENT_TYPE,
                    BaseConstants.TOAST_CONTENT_TYPE_STR
            )
            put(BaseConstants.TOAST_KEY_CONTENT, str)
            if (duration != null) {
                put(BaseConstants.TOAST_KEY_DURATION, duration)
            }
        }

        showToast(map)
    }

    protected fun showToast(@StringRes resId: Int) {
        showToast(resId, null)
    }

    private fun showToast(@StringRes resId: Int, duration: Int?) {
        val map = HashMap<String, Any>().apply {
            put(
                    BaseConstants.TOAST_KEY_CONTENT_TYPE,
                    BaseConstants.TOAST_CONTENT_TYPE_RESID
            )
            put(BaseConstants.TOAST_KEY_CONTENT, resId)
            if (duration != null) {
                put(BaseConstants.TOAST_KEY_DURATION, duration)
            }
        }
        showToast(map)
    }

    protected fun backPress() {
        backPress(null)
    }

    protected fun finishPage() {
        finishPage(null)
    }

    protected fun getUUID():String = UUID.randomUUID().toString().replace("-","")

    companion object{
        @JvmStatic
        fun <T:BaseViewModel> createViewModelFactory(viewModel:T):ViewModelProvider.Factory{
            return ViewModelFactory(viewModel)
        }
    }

}


class ViewModelFactory(private val viewModel:BaseViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }

}