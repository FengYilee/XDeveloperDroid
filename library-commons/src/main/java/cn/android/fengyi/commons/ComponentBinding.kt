package cn.android.fengyi.commons

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.android.fengyi.commons.adapter.BaseBindingRecyclerAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

@BindingAdapter("bindAdapter")
fun <T>bindingRecyclerAdapter(recyclerView: RecyclerView,adapter:T){
    if (adapter is BaseBindingRecyclerAdapter<*,*>){
        recyclerView.adapter = adapter
    }
}

@BindingAdapter(
    value = ["refreshing", "moreLoading", "hasMore"],
    requireAll = false
)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    refreshing: Boolean,
    moreLoading: Boolean,
    hasMore: Boolean

) {//状态绑定，控制停止刷新
    if (!refreshing) smartLayout.finishRefresh()
    if (!moreLoading) smartLayout.finishLoadMore()
    smartLayout.setEnableLoadMore(hasMore)
}

@BindingAdapter(
    value = ["autoRefresh"]
)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    autoRefresh: Boolean
) {//控制自动刷新
    if (autoRefresh) smartLayout.autoRefresh()
}

@BindingAdapter(//下拉刷新，加载更多
    value = ["onRefreshListener", "onLoadMoreListener"],
    requireAll = false
)
fun bindListener(
    smartLayout: SmartRefreshLayout,
    refreshListener: OnRefreshListener?,
    loadMoreListener: OnLoadMoreListener?
) {
    smartLayout.setOnRefreshListener(refreshListener)
    smartLayout.setOnLoadMoreListener(loadMoreListener)
}
