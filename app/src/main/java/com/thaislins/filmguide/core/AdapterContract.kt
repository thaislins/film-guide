package com.thaislins.filmguide.core

import com.thaislins.filmguide.modules.home.model.Film

interface AdapterContract {
    fun set(list: List<Film>?)
}