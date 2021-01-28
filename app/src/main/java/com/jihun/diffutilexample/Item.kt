package com.jihun.diffutilexample

sealed class SelectItem {
    data class Item(
        var name: String? = null,
        var isSelected: Boolean = false
    ): SelectItem()
}