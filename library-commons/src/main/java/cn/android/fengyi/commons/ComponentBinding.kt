package cn.android.fengyi.commons

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.android.fengyi.commons.adapter.BaseBindingRecyclerAdapter
import cn.android.fengyi.commons.adapter.BaseRecyclerAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindAdapter")
fun <T>bindingRecyclerAdapter(recyclerView: RecyclerView,adapter:T){
    if (adapter is BaseBindingRecyclerAdapter<*, *>){
        recyclerView.adapter = adapter
    } else if (adapter is BaseRecyclerAdapter<*>){
        recyclerView.adapter = adapter
    }
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter("bindLayoutManager")
fun <T>bindLayoutManager(recyclerView: RecyclerView,layoutManager:RecyclerView.LayoutManager){
    recyclerView.layoutManager = layoutManager
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
