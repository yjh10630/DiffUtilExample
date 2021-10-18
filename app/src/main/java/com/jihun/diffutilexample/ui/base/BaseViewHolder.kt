package com.jihun.diffutilexample.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun onBind(data: Any?) {}
}