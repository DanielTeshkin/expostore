package com.expostore.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "info_chat")
data class InfoChat(
    @PrimaryKey(autoGenerate = true) val id:Long=0,
    @ColumnInfo(name="id_chat") val id_chat: List<String> = listOf(),
    @ColumnInfo(name="author") val author:String = "",
    @ColumnInfo(name="username") val username:String ="",
    @ColumnInfo(name="name") val name:String ="",

    @ColumnInfo(name="product_name") val product_name:List<String> = listOf(),
    @ColumnInfo(name="product_images") val product_images:List<String> = listOf()

)
class InfoTypeConverter{
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}
