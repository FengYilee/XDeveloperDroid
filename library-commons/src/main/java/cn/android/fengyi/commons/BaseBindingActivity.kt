package cn.android.fengyi.commons

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.android.fengyi.commons.viewmodel.ViewBehavior

abstract class BaseBindingActivity<B:ViewDataBinding>:AppCompatActivity(),ViewBehavior {

    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDataBinding()
        init(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    protected open fun injectDataBinding(){
        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
        mBinding.lifecycleOwner = this
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int


    protected abstract fun init(savedInstanceState: Bundle?)


}