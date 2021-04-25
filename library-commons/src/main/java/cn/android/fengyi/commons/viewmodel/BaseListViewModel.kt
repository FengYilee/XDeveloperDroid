package cn.android.fengyi.commons.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData

abstract class BaseListViewModel :BaseViewModel(){

    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefreshEvent = MutableLiveData<Boolean>()

    var page = 1
    var pageSize = 20

    val refreshOrLoadMoreEvent = MutableLiveData<Int>()
    var refreshOrLoadMore = 0       //0:刷新; 1:加载更多

    init {
        hasMore.value = true
    }

    fun refresh() {
        refreshOrLoadMore = 0
        refreshing.value = true
        page = 1
        loadData()
    }

    fun loadMore(){
        refreshOrLoadMore = 1
        moreLoading.value = true
        page++
        loadData()
    }


    fun autoRefresh() {
        autoRefreshEvent.value = true
    }

    abstract fun loadData()

    fun finishRefresh(){
        refreshOrLoadMoreEvent.postValue(refreshOrLoadMore)
    }
}