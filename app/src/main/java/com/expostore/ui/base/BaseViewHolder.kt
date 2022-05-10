package com.expostore.ui.base

import androidx.appcompat.view.menu.MenuView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T,R:ViewBinding> {

    abstract fun bind(item:T)
}