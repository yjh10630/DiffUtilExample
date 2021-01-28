package com.jihun.diffutilexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.databinding.ViewItemBinding

class MainListAdapter: RecyclerView.Adapter<ItemViewHolder>() {

    var items: MutableList<SelectItem.Item>? = null
        set(value) {
            value?.let {
                diffUtilExtensions(field, it,
                    itemCompare = { o, n ->
                        o?.name == n?.name
                    }, contentCompare = { o, n ->
                        o?.isSelected == n?.isSelected
                    })

                field?.let {
                    it.clear()
                    it.addAll(value)
                } ?: run {
                    field = value
                }
            }
        }

    override fun getItemCount(): Int = items?.size ?: 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items?.getOrNull(position))
    }
}