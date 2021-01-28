package com.jihun.diffutilexample

import androidx.recyclerview.widget.RecyclerView
import com.jihun.diffutilexample.databinding.ViewItemBinding

class ItemViewHolder(private val binding: ViewItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: SelectItem.Item?) {
        with (binding) {
            name.text = data?.name ?: ""
            checkBox.isChecked = data?.isSelected ?: false
        }

        itemView click {

        }
    }
}