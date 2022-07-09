package com.expostore.api.request

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.google.gson.annotations.SerializedName
import java.util.*


open  class  ChatCreateRequest(
   val tender: String
)
//@SerializedName("product")
 class TenderChat(
    @SerializedName("tender")  val id: String)

class ProductChat(
    @SerializedName("product")  val id: String)



@SuppressLint("SimpleDateFormat")
fun getDate(): String {
    var today =  Date();

    var dd = today.date;
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.year;
    var hh = today.getHours();
    var m = today.getMinutes();
    var secs = today.getSeconds();
    return yyyy.toString() +"-"+mm+"-"+dd+"T"+hh+":"+m+":"+secs
}
