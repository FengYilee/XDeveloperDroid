package cn.android.fengyi.commons

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.android.fengyi.commons.viewmodel.BaseViewModel
import cn.android.fengyi.commons.viewmodel.ViewBehavior
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions

/**
 * 类描述：Fragment基类
 * 创建人： Fengyi.Li
 * 创建时间：2021/3/3 9:43
 */
abstract class BaseViewModelFragment<B : ViewDataBinding,VM:BaseViewModel> : BaseBindingFragment<B>(),ViewBehavior {

    protected lateinit var viewModel: VM
            private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) return rootView

        injectDataBinding(inflater, container)
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        return rootView
    }

    protected fun injectViewModel(){
        val vm = createViewModel()
        viewModel = ViewModelProvider(this,BaseViewModel.createViewModelFactory(vm)).get(vm::class.java)
        viewModel.application = activity!!.application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    protected open fun initInternalObserver(){
        viewModel.loadingEvent.observe(this, Observer<Boolean> {
            showLoadingUI(it)
        })

        viewModel.emptyPageEvent.observe(this, Observer {
            showEmptyUI(it)
        })

        viewModel.toastEvent.observe(this, Observer {
            showToast(it)
        })

        viewModel.backPressEvent.observe(this, Observer {
            backPress(it)
        })

        viewModel.finishPageEvent.observe(this, Observer {
            finishPage(it)
        })

    }

    protected fun requestPermissions(permission: String?, callback: OnPermissionCallback?) {
        XXPermissions.with(this)
                .permission(permission)
                .request(callback)
    }

    protected fun requestPermissions(permissions: Array<String>, callback: OnPermissionCallback?) {
        XXPermissions.with(this)
                .permission(permissions)
                .request(callback)
    }

    abstract fun createViewModel(): VM

    override fun showLoadingUI(isShow: Boolean) {

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(map: Map<String, *>) {
        Toast.makeText(activity,map[BaseConstants.TOAST_KEY_CONTENT].toString(), Toast.LENGTH_LONG).show()
    }

    override fun navigate(page: Any?) {

    }

    override fun backPress(arg: Any?) {

    }

    override fun finishPage(arg: Any?) {

    }
}