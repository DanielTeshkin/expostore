package com.expostore.ui.fragment.chats.general


import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.ui.fragment.chats.toBase64
import java.io.ByteArrayOutputStream

import kotlin.collections.ArrayList


class ImageMessage() {

    fun bitmaplist(uri:ArrayList<Uri>,context: Context):ArrayList<Bitmap> {
        val bitmapList=ArrayList<Bitmap>()
        uri.map{
            Glide.with(context).asBitmap().load(it).into(object :
                CustomTarget<Bitmap>(720,720){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        }
        return bitmapList
    }


    fun encodeBitmapList(list:ArrayList<Bitmap>):ArrayList<String>{
        val urls=ArrayList<String>()
        list.map { encode(it)?.let { bitmap-> urls.add(bitmap) } }
        return urls
    }


    fun encode( bitmap: Bitmap):String?{
      return bitmap.toBase64()
    }
    fun encode(uri: Uri,context: Context):String{

        var os = ByteArrayOutputStream()
        var inputStream = context.contentResolver.openInputStream(uri)
        var byteArray = inputStream!!.readBytes()
        return Base64.encodeToString(byteArray,Base64.DEFAULT)

    }


}
private fun ArrayList<Uri>.bitmap(context:Context):ArrayList<Bitmap>{

    return ImageMessage().bitmaplist(this,context)
}
private fun ArrayList<Bitmap>.encode():ArrayList<String>{
    return ImageMessage().encodeBitmapList(this)
}
fun ImageMessage.save(list:ArrayList<Uri>,context: Context):ArrayList<String>{
   return bitmaplist(list,context).encode()
}

