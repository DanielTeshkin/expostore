@file:Suppress("UNCHECKED_CAST")

package com.expostore.ui.fragment.chats

import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.api.pojo.getchats.UserResponse
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream


/**
 * @author Teshkin Daniel
 */
fun ResponseMainChat.imagesProduct(): Array<String> {
    return itemsChatResponse!!
        .map { it!!.product }
        .map { it!!.imageResponsImages[0].file!! }.toTypedArray()
}
fun ResponseMainChat.chatsId():Array<String>{
    val list=ArrayList<String>()
      itemsChatResponse!!
        .map {list.add(it.id!! ) }
    return list.toTypedArray()
}

fun ResponseMainChat.checkNumber(): UserResponse {
    return if(buyer!!.username==request_user!!.username) seller!!
    else buyer
}
fun ResponseMainChat.identify():Array<String>{
    val user=checkNumber()
    return  arrayOf(user!!.username!!,checkName(user),user.avatar?:"",request_user!!.id!!)
}
fun checkName(userResponse:UserResponse):String{
    return if(userResponse!!.firstName!=null){

        userResponse!!.firstName+" "+userResponse!!.lastName

    } else userResponse!!.username!!
}

fun ResponseMainChat.productsName():Array<String>{
    return itemsChatResponse!!.map { it.product?.name!! }.toTypedArray()
}
fun ResponseMainChat.lastMessage():String{
    return if(itemsChatResponse!=null) {
        val index = itemsChatResponse[0].messageResponses.lastIndex
        itemsChatResponse[0].messageResponses[index].text ?: "нет собщений"
    }
    else{
        "нет сообщений"
    }
}


fun ImageView.loadAvatar(url: String){
    Glide.with(context)
        .load(url)
        .circleCrop()
        .into(this)
}
fun ImageView.loadTabImage(url:String){
    Glide.with(context)
        .load(url)
        .override(100, 100)
        .transform(RoundedCorners(15))
        .into(this)
}
fun ImageView.loadImage(url:String){
    Glide.with(context)
        .load(url)
        .override(300,400 )
        .transform(RoundedCorners(10))
        .into(this)
}

fun CoroutineScope.repeat(repeatMillis: Long, action: () -> Unit) = this.launch {
    withContext(Dispatchers.IO) {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    }
}
     fun Bitmap.toBase64(): String? {
        val bytes = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val byteArray: ByteArray = bytes.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
fun RecyclerView.down(position:Int){
    this.smoothScrollToPosition(position)
}



