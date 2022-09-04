@file:Suppress("UNCHECKED_CAST")

package  com.expostore.ui.fragment.chats

import android.content.ClipData
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.expostore.R
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.DataMapping.User
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.fragment.chats.list.ChatsRecyclerViewAdapter
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream


/**
 * @author Teshkin Daniel
 */
fun MainChat.imagesProduct(): Array<String> {
    val list= mutableListOf<String>()
     for (i in itemsChat.indices){
         if (itemsChat[i].product!=null)list.add(itemsChat[i].product?.images?.get(0)?.file?:"")
         else if(itemsChat[i].tender!=null) itemsChat[i].tender?.images?.get(0)?.let { list.add(it.file) }
     }

   return list.toTypedArray()
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
    return  arrayOf(user.username,checkName(user),user.avatar?.file?:"", request_user.id)
}
fun checkName(user: User):String{
    return if(user.firstName!=null&&user.lastName!=null){
        user.firstName+" "+user.lastName
    } else user.username
}

fun MainChat.productsName():Array<String>{
    val list = mutableListOf<String>()
    for (i in itemsChat.indices){
        if(itemsChat[i].product!=null) itemsChat[i].product?.name?.let { list.add(it) }
        else itemsChat[i].tender?.name?.let { list.add(it) }
    }
    return list.toTypedArray()
}
fun MainChat.firstMessage():String{
    return if(itemsChat[0].messages?.size!=0) {
        val index = 0
        when {
            itemsChat[0].messages?.get(index)?.text ?:"" !="" -> {
                itemsChat[0].messages?.get(index)?.text?:""
            }
            itemsChat[0].messages?.get(index)?.images?.size!=0 -> " новое изображение"
            else -> {"Новый файл" }
        }
    }
    else{
        "нет сообщений"
    }
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

fun ImageView.reviewImage(url:String){
    Glide.with(context)
        .load(url)
        .centerCrop()
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
    this.scrollToPosition(position)
}
fun RecyclerView.downSmooth(position:Int){
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
    when(url.isEmpty()){
        true ->Glide.with(context).load(R.drawable.avatar).circleCrop().placeholder(R.drawable.ic_avatar).into(this)
        else ->{Glide.with(context)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_avatar)
            .into(this)}}
}
fun ImageView.loadChatAvatar(url: String){
    when(url.isEmpty()){
        true ->Glide.with(context).load(R.drawable.avatar).circleCrop().placeholder(R.drawable.ic_avatar).into(this)
        else ->{Glide.with(context)
            .load(url)
            .circleCrop()

            .into(this)}}
}

fun  View.visible(state:Boolean){
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
fun EditText.textChange(imageFirst: ImageView,imageSecond: ImageView){
    val stroke = this.text.toString()
    imageFirst.visible(stroke.isNotEmpty())
    imageSecond.visible(stroke.isEmpty())
}

fun RecyclerView.install(manager: LinearLayoutManager,adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>){
         this.layoutManager=manager
         this.adapter=adapter
}
fun RecyclerView.install(manager: LinearLayoutManager,adapter:ChatsRecyclerViewAdapter){
    this.layoutManager=manager
    this.adapter=adapter
}




