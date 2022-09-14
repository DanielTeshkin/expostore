package com.expostore.model.chats

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfoItemChat(
    val name:String?="",
    val username:String?="",
    val  id_list:Array<String>?= arrayOf(),
    val product_names:Array<String>?= arrayOf(),
    val id_image:Array<String>?= arrayOf(),
    val  author:String?=""):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoItemChat

        if (name != other.name) return false
        if (username != other.username) return false
        if (id_list != null) {
            if (other.id_list == null) return false
            if (!id_list.contentEquals(other.id_list)) return false
        } else if (other.id_list != null) return false
        if (product_names != null) {
            if (other.product_names == null) return false
            if (!product_names.contentEquals(other.product_names)) return false
        } else if (other.product_names != null) return false
        if (id_image != null) {
            if (other.id_image == null) return false
            if (!id_image.contentEquals(other.id_image)) return false
        } else if (other.id_image != null) return false
        if (author != other.author) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (id_list?.contentHashCode() ?: 0)
        result = 31 * result + (product_names?.contentHashCode() ?: 0)
        result = 31 * result + (id_image?.contentHashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        return result
    }
}