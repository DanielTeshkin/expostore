@file:Suppress("UNCHECKED_CAST")

package com.expostore.ui.fragment.chats

import android.content.ClipData
import android.graphics.Bitmap
import android.media.tv.TvContract
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.expostore.R
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.api.pojo.getchats.UserResponse
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.DataMapping.User
import com.expostore.model.profile.ProfileModel
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream


/**
 * @author Teshkin Daniel
 */
fun MainChat.imagesProduct(): Array<String> {
    return itemsChat
        .map { it!!.product }
        .map { it!!.images[0].file }.toTypedArray()
}
fun MainChat.chatsId():Array<String>{
    val list=ArrayList<String>()
      itemsChat
        .map {list.add(it.id ) }
    return list.toTypedArray()
}

fun MainChat.checkNumber(): User {
    return if(buyer.username== request_user.username) seller
    else buyer
}
fun MainChat.identify():Array<String>{
    val user=checkNumber()
    return  arrayOf(user.username,checkName(user),user.avatar?:"", request_user.id)
}
fun checkName(user: User):String{
    return if(user!!.firstName!=null){

        user!!.firstName+" "+user!!.lastName

    } else user!!.username!!
}

fun MainChat.productsName():Array<String>{
    return itemsChat!!.map { it.product?.name!! }.toTypedArray()
}
fun MainChat.lastMessage():String{
    return if(itemsChat!![0].messages.size!=0) {
        val index = itemsChat!![0].messages.lastIndex
        if(itemsChat[0].messages[index].text!=""){
        itemsChat[0].messages[index].text}
        else if(itemsChat[0].messages[index].images?.size!=0)
            " новое изображение"
        else{"Новый файл" }
    }
    else{
        "нет сообщений"
    }
}

fun ImageView.loadAvatar(profileModel: ProfileModel){
    when(profileModel.avatar){
        null->Glide.with(context).load(R.mipmap.avatar).circleCrop().into(this)
    else ->{Glide.with(context)
        .load(profileModel.avatar)
        .circleCrop()
        .into(this)}}
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
fun ClipData.listPath():ArrayList<Uri>{
    val size=this.itemCount
    val list=ArrayList<Uri>()
    for (i in 0 until size) {
        list.add(this.getItemAt(i).uri)
    }
    return list
}
fun ImageView.loadAvatar(url: String){
    when(url.isBlank()){
        true ->Glide.with(context).load(R.mipmap.avatar).circleCrop().into(this)
        else ->{Glide.with(context)
            .load(url)
            .circleCrop()
            .into(this)}}
}

fun ImageView.visible(state:Boolean){
    visibility = when(state){
        true -> View.VISIBLE
        false -> View.GONE
    }

}
fun ProgressBar.visible(state: Boolean){
    visibility = when(state){
        true -> View.VISIBLE
        false -> View.GONE
    }

}




