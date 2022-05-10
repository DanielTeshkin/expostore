package com.expostore.ui.base


import android.content.ClipData
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

interface BaseAdapter<V:ViewBinding,T> {

    fun isRelative(item:T):Boolean

    @LayoutRes
    fun getLayoutId():Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent:ViewGroup
    ):BaseViewHolder<T,V>


}