package com.expostore.ui.fragment.product.addproduct.adapter.utils

import android.net.Uri

interface ImagesEdit {
    fun openGallery()
    fun removePhoto(string: String)
    fun cropImage(uri:Uri)
}