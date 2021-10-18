package com.jihun.diffutilexample.ui

import com.jihun.diffutilexample.data.ResponseSampleDataInfo
import com.jihun.diffutilexample.databinding.ViewItemBinding
import com.jihun.diffutilexample.ui.base.BaseViewHolder

class ItemViewHolder(private val binding: ViewItemBinding): BaseViewHolder(binding.root) {
    override fun onBind(data: Any?) {
        (data as? ResponseSampleDataInfo)?.let {
            initView(it)
        }
    }

    private fun initView(data: ResponseSampleDataInfo) {
        with(binding) {
            name.text = data.name
            checkBox.isChecked = data.isChecked
        }
    }
}