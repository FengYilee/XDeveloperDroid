package com.lfy.components.adapters

import android.view.View
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import cn.android.fengyi.commons.adapter.BaseBindingRecyclerAdapter
import cn.android.fengyi.commons.adapter.BaseViewHolder
import com.lfy.components.R
import com.lfy.components.bean.BottomSearchBean
import com.lfy.components.databinding.ItemBottomSearchBinding

/**
 * Create by FengYi.Lee on 2021/10/27.
 * desc:
 */
class BottomSelectSearchAdapter:
    BaseBindingRecyclerAdapter<ItemBottomSearchBinding, BottomSearchBean>(),
    Filterable {

    var mFilterList:MutableList<BottomSearchBean> = ArrayList()

    override fun onBindItem(binding: ItemBottomSearchBinding, item: BottomSearchBean, position: Int) {
        binding.tvText.text = mFilterList[position].name

        if (position == itemCount - 1){
            binding.viewLine.visibility = View.GONE
        } else {
            binding.viewLine.visibility = View.VISIBLE
        }
    }

    override fun onBindItem(holder: BaseViewHolder, item: BottomSearchBean, position: Int) {
        val binding:ItemBottomSearchBinding = DataBindingUtil.getBinding(holder.itemView)!!
        onBindItem(binding,item,position)
        binding.executePendingBindings()
        getItemClickListener()?.let {
                listener->
            holder.itemView.setOnClickListener {
                listener.onItemClick(binding,mFilterList[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mFilterList.size
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_bottom_search

    override fun getFilter(): Filter = object:Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint.toString()
            if (charString.isEmpty()){
                mFilterList = getItems()!!
            } else {
                val filteredList: MutableList<BottomSearchBean> = ArrayList()
                for (bean in getItems()!!) {
                    //这里根据需求，添加匹配规则
                    if (bean.name!!.contains(charString.toLowerCase())) {
                        filteredList.add(bean)
                    }
                }
                mFilterList = filteredList
            }

            val filterResults = FilterResults()
            filterResults.values = mFilterList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            mFilterList = results.values as ArrayList<BottomSearchBean>
            notifyDataSetChanged()
        }

    }


}