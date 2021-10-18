package com.jihun.diffutilexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.ViewTypeConst
import com.jihun.diffutilexample.data.MainDataSet
import com.jihun.diffutilexample.databinding.ViewEmptyBinding
import com.jihun.diffutilexample.databinding.ViewItemBinding
import com.jihun.diffutilexample.databinding.ViewItemSearchEmptyBinding
import com.jihun.diffutilexample.ui.base.BaseViewHolder
import com.jihun.diffutilexample.ui.base.EmptyViewHolder

class MainListAdapter: RecyclerView.Adapter<BaseViewHolder>() {
    var items: MutableList<MainDataSet>? = null

    fun submitList(newList: MutableList<MainDataSet>?, diffResult: DiffUtil.DiffResult) {
        if (newList.isNullOrEmpty()) return
        items?.let {
            it.clear()
            it.addAll(newList)
        } ?: run {
            items = newList
        }
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items?.size ?: 0
    override fun getItemViewType(position: Int): Int = items?.getOrNull(position)?.viewType?.ordinal ?: ViewTypeConst.EMPTY.ordinal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when(ViewTypeConst.values()[viewType]) {
            ViewTypeConst.ITEM -> ItemViewHolder(ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ViewTypeConst.SEARCH_EMPTY -> ItemSearchEmptyViewHolder(
                ViewItemSearchEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> EmptyViewHolder(ViewEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(items?.getOrNull(position)?.data)
    }
}