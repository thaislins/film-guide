package com.thaislins.filmguide.core

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("items")
fun set(recyclerView: RecyclerView, list: List<Any>?) {
    recyclerView.adapter.let {
        if (it is AdapterContract) {
            it.set(list)
        }
    }
}
