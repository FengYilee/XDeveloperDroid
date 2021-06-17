package cn.android.fengyi.commons

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.android.fengyi.commons.viewmodel.BaseViewModel
import cn.android.fengyi.commons.viewmodel.ViewBehavior
import cn.android.fengyi.net.dialog.LoadingDialog
import cn.android.fengyi.net.dialog.LoadingDialogManager
import com.alibaba.android.arouter.launcher.ARouter
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
    var count = 0


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

    override fun onResume() {
        super.onResume()
        LoadingDialogManager.instance?.setDialog(LoadingDialog(this))
    }

    override fun onPause() {
        super.onPause()
        LoadingDialogManager.instance?.removeDialog()
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
            Log.i("Observer","${Thread.currentThread().name} - ${++count}")
            showToast(it)
        })

        viewModel.pageNavigationEvent.observe(this, Observer {
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

    protected open fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        var defaultColor = R.color.design_default_color_on_primary
        if (stateBarColor() != 0)
            defaultColor = stateBarColor()
        XStatusBarHelper.tintStatusBar(this, resources.getColor(defaultColor),0.0f)
    }

    protected open fun stateBarColor():Int = 0

    override fun showLoadingUI(isShow: Boolean) {

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(map: Map<String, *>) {
        Toast.makeText(this,map[BaseConstants.TOAST_KEY_CONTENT].toString(),Toast.LENGTH_LONG).show()
    }

    override fun navigate(page: Any?) {
        page?.let {
            ARouter.getInstance()
                .build(it.toString())
                .navigation()
            finish()
        }
    }

    protected open fun navigate(page: Any?, bundle: Bundle) {
        page?.let {
            ARouter.getInstance()
                .build(it.toString())
                .with(bundle)
                .navigation()
            finish()
        }
    }

    override fun backPress(arg: Any?) {
        finish()
    }

    override fun finishPage(arg: Any?) {
        finish()
    }
}