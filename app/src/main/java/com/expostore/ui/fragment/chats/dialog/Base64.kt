package com.expostore.ui.fragment.chats.dialog


import android.graphics.Bitmap

import com.expostore.ui.fragment.chats.toBase64



class ImageMessage() {

    fun encode( bitmap: Bitmap):String?{
      return bitmap.toBase64()
    }

}
