package cn.android.fengyi.commons

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.android.fengyi.commons.viewmodel.BaseViewModel
import cn.android.fengyi.commons.viewmodel.ViewBehavior
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import me.shihao.library.XStatusBarHelper

/**
 * 类描述：Activity基类
 * 创建人： Fengyi.Li
 * 创建时间：2021/3/2 15:40
 */
abstract class BaseViewModelActivity<B : ViewDataBinding,VM:BaseViewModel> : BaseBindingActivity<B>(),ViewBehavior {

    private lateinit var viewModel:VM


    protected abstract val statusBarColor: Int

    private fun injectViewModel(){
        val vm = createViewModel()
        viewModel = ViewModelProvider(this,BaseViewModel.createViewModelFactory(vm)).get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    override fun init(savedInstanceState: Bundle?) {
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        initStatusBar()
    }

    fun getActivityViewModel():VM{
        return viewModel
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

    protected abstract fun createViewModel():VM

    /**
     * 初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)


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

    protected fun initStatusBar() {
        XStatusBarHelper.tintStatusBar(this, resources.getColor(statusBarColor))
    }

    override fun showLoadingUI(isShow: Boolean) {

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(map: Map<String, *>) {

    }

    override fun navigate(page: Any) {

    }

    override fun backPress(arg: Any?) {

    }

    override fun finishPage(arg: Any?) {

    }
}