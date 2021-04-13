package cn.android.fengyi.commons

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.android.fengyi.commons.viewmodel.BaseViewModel
import cn.android.fengyi.commons.viewmodel.ViewBehavior
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

    protected fun initInternalObserver(){
        viewModel.loadingEvent.observe(this, Observer<Boolean> {
            showLoadingUI(it)
        })

        viewModel.emptyPageEvent.observe(this, Observer {
            showEmptyUI(it)
        })

        viewModel.toastEvent.observe(this, Observer {
            navigate(it)
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
}