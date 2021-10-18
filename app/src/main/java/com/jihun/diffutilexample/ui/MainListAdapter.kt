package com.jihun.diffutilexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.data.MainDataSet
import com.jihun.diffutilexample.databinding.ViewItemBinding
import com.jihun.diffutilexample.ui.base.BaseViewHolder

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(items?.getOrNull(position)?.data)
    }
}