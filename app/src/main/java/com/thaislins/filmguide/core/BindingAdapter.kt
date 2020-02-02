package com.thaislins.filmguide.core

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thaislins.filmguide.modules.film.model.Film

object BindingAdapters {
    @BindingAdapter("items")
    @JvmStatic
    fun setCards(recyclerView: RecyclerView, list: List<Film>?) {
        recyclerView.adapter.let {
            if (it is AdapterContract) {
                it.set(list)
            }
        }
    }
}