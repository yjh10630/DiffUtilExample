package com.jihun.diffutilexample.ui.base

import com.jihun.diffutilexample.databinding.ViewEmptyBinding

class EmptyViewHolder(private val binding: ViewEmptyBinding): BaseViewHolder(binding.root) {
    override fun onBind(data: Any?) {}
}